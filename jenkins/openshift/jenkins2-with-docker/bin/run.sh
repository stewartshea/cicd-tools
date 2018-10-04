#!/bin/bash

mkdir /var/lib/jenkins/init.groovy.d/
cp /groovy/* /var/lib/jenkins/init.groovy.d/
chmod 755 /var/lib/jenkins/init.groovy.d/*

sh /usr/libexec/s2i/run