FROM openshift/jenkins-2-centos7
COPY plugins.txt /plugins.txt

USER ROOT
RUN /usr/local/bin/install-plugins.sh /plugins.txt