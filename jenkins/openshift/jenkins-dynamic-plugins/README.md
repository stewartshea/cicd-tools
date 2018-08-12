### Purpose
Extend the openshift jenkins image leveraging the plugin install script leveraging 
input from an Environment Variable. Intended to be run in the `openshift` 
namespace and replaces the existing image stored inside the cluster. 

### Installation
#### OpenShift

These steps will install this into OpenShift and configure the image
to be tagged as jenkins:ext-plugins

To add plugins, simply list each plugin and version in the plugins.txt 
file. 

```
oc project openshift
oc new-build https://github.com/stewartshea/cicd-tools \
    --context-dir=jenkins/openshift/jenkins-dynamic-plugins \
    --to=jenkins:ext-plugins \
    --name=jenkins-ext-plugins
```

Users can now change their Jenkins image tag to `ext-plugins`, or if you want all 
standard images running `latest` to get this new image, tag the new image as latest: 

```
oc tag jenkins:ext-plugins jenkins:latest
```