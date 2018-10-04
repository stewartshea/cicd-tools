#!/bin/bash

mkdir /var/lib/jenkins/groovy.init.d/
cp /groovy/* /var/lib/jenkins/groovy.init.d/
sh /usr/libexec/s2i/run