# Purpose
Extend the openshift jenkins image to inlclude docker and custom configs. 
Intended to be run in the `openshift`namespace and replaces the existing 
image stored inside the cluster. 

```
oc project openshift
oc new-build https://github.com/stewartshea/cicd-tools \
    --context-dir=jenkins/openshift/jenkins2-with-docker \
    --to=jenkins:latest \
    --name=jenkins
```





"He was a dreamer, a thinker, a speculative philosopher... or, as his wife would have it, an idiot." - Douglas Adams
