FROM openshift/jenkins-2-centos7

## Add installer script
ADD install_jenkins_plugin.sh .

RUN chmod +x install_jenkins_plugin.sh \
    && ./install_jenkins_plugin.sh $PLUGIN_LIST
