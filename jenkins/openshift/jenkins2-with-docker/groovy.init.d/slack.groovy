import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.impl.*
import hudson.util.Secret
import java.nio.file.Files
import jenkins.model.Jenkins
import net.sf.json.JSONObject
import org.jenkinsci.plugins.plaincredentials.impl.*
 
// hacky env workaround due to issues with the the secret env variables
def token_out = new StringBuffer(), token_err = new StringBuffer()
def token = "env".execute() | "grep SLACK_token".execute() | ['awk', '-F', "=",  '{ print $2 }'].execute()
token.consumeProcessOutput(token_out, token_err)
token.waitForOrKill(1000)
token_out=token_out.toString()

def base_url_out = new StringBuffer(), base_url_err = new StringBuffer()
def base_url = "env".execute() | "grep SLACK_base_url".execute() | ['awk', '-F', "=",  '{ print $2 }'].execute()
base_url.consumeProcessOutput(base_url_out, base_url_err)
base_url.waitForOrKill(1000)
base_url_out=base_url_out.toString()

def team_domain_out = new StringBuffer(), team_domain_err = new StringBuffer()
def team_domain = "env".execute() | "grep SLACK_team_domain".execute() | ['awk', '-F', "=",  '{ print $2 }'].execute()
team_domain.consumeProcessOutput(team_domain_out, team_domain_err)
team_domain.waitForOrKill(1000)
team_domain_out=team_domain_out.toString()

// get envVars (ran into issues here, used the above hack)
// def env=System.getenv()
// slack_token=env['SLACK_token']
// slack_base_url=env['SLACK_base_url']
// slack_webhook_url=env['SLACK_webhook_url']

// get jenkins slack URL
def route_out = new StringBuffer(), route_err = new StringBuffer()
def oc_route = "oc get route".execute() | "grep jenkins".execute() | ['awk', '{ print $2 }'].execute()
oc_route.consumeProcessOutput(route_out, route_err)
oc_route.waitForOrKill(1000)
def route = "http://$route_out" 

// parameters
def slackCredentialParameters = [
  description:  'Slack Jenkins integration token',
  id:           "slack-token",
  secret:       "$token_out"
]
 
def slackParameters = [
  slackBaseUrl:             base_url_out,
  slackBotUser:             'false',
  slackBuildServerUrl:      route_out,
 // slackRoom:                '#jenkins',
  slackSendAs:              'Jenkins',
  slackTeamDomain:          team_domain_out,
  slackToken:               'slack-token',
  slackTokenCredentialId:   'slack-token'
]
 
// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()
 
// get credentials domain
def domain = Domain.global()
 
// get credentials store
def store = jenkins.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
 
// get Slack plugin
def slack = jenkins.getExtensionList(jenkins.plugins.slack.SlackNotifier.DescriptorImpl.class)[0]
 
// define secret
def secretText = new StringCredentialsImpl(
  CredentialsScope.GLOBAL,
  slackCredentialParameters.id,
  slackCredentialParameters.description,
  Secret.fromString(slackCredentialParameters.secret)
)
 
// define form and request
JSONObject formData = ['slack': ['tokenCredentialId': 'slack-token']] as JSONObject
def request = [getParameter: { name -> slackParameters[name] }] as org.kohsuke.stapler.StaplerRequest
 
// add credential to Jenkins credentials store
store.addCredentials(domain, secretText)
 
// add Slack configuration to Jenkins
slack.configure(request, formData)
 
// save to disk
slack.save()
jenkins.save()
