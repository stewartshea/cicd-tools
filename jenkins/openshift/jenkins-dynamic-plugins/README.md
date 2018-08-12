### Purpose
Extend the openshift jenkins image with a plugin install script leveraging 
input from an Environment Variable. Intended to be run in the `openshift` 
namespace and replaces the existing image stored inside the cluster. 

### Installation
####OpenShift

These steps will install this into OpenShift and configure the image
to be tagged as jenkins:ext-plugins

The environment variable `PLUGINS_LIST` is required and at least 1 
plugin is required to be installed. 

```
oc project openshift
oc new-build https://github.com/stewartshea/cicd-tools --context-dir=jenkins/openshift/jenkins-dynamic-plugins --to=jenkins:ext-plugins -e PLUGIN_LIST=pipeline-npm --name=jenkins-ext-plugins
```

If you want all standard images running `latest` to get this new image, tag 
the new image as latest: 

```
oc tag jenkins:ext-plugins jenkins:latest
```