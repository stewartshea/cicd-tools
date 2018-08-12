FROM openshift/jenkins-2-centos7

## Add installer script
ADD install_jenkins_plugins.sh .

RUN chmod +x install_jenkins_plugins.sh \
    && ./install_jenkins_plugins.sh $PLUGIN_LIST
