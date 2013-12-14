#!/bin/bash
export MAVEN_OPTS="-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n -Xms128m -Xmx350m -XX:MaxPermSize=1280m"
