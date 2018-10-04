FROM openshift/jenkins-2-centos7


### Install Docker Client
RUN curl https://get.docker.com/builds/Linux/x86_64/docker-latest.tgz | tar xvz -C /tmp/ && mv /tmp/docker/docker /usr/bin/docker

## Copy in custom init scripts
USER 0
RUN mkdir /groovy
COPY groovy.init.d/   /groovy
COPY bin/run.sh /run.sh
RUN chmod 777 /run.sh

USER 1001
CMD ["/run.sh"]