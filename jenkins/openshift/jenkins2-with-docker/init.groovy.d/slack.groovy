import hudson.model.*
import jenkins.model.*
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl

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



jenkins = jenkins.model.Jenkins.getInstance()
def slack = jenkins.getDescriptorByType(jenkins.plugins.slack.SlackNotifier.DescriptorImpl)
slack.teamDomain = "$team_domain_out"
slack.token = "$token_out"
slack.sendAs = "jenkins"
slack.save()
jenkins.save()
