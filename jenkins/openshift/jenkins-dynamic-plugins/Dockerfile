FROM openshift/jenkins-2-centos7

## Add installer script
USER root
ADD install_jenkins_plugins.sh .

RUN chmod 777 install_jenkins_plugins.sh \
    && ./install_jenkins_plugins.sh $PLUGIN_LIST

USER 1001