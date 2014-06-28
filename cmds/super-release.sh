#!/bin/bash -ex

CLOSE_VERSION="$1"
SNAPSHOT_VERSION="$2"

STOP=0

source env/standalone/env.sh

if [ "x$CLOSE_VERSION" == "x" ]; then 
	STOP=1;
fi;

if [ "x$SNAPSHOT_VERSION" == "x" ]; then 
	STOP=1;
fi;

if git status -s | grep ".\\+"; then 
	STOP=1; 
fi

if [ "x$STOP" == "x0" ]; then
	mvn clean install
fi

if git status -s | grep ".\\+"; then 
	STOP=1; 
fi

if [ "x$STOP" == "x0" ]; then
	mvn versions:set -DnewVersion=$CLOSE_VERSION
	git commit -am "releasing raidenjpa-$CLOSE_VERSION"
	git tag raidenjpa-$CLOSE_VERSION
	git push origin raidenjpa-$CLOSE_VERSION
 
	mvn clean install -Dmaven.test.skip.exec
	mvn deploy -e -DreleaseMail -Dmaven.test.skip.exec

	mvn versions:set -DnewVersion=$SNAPSHOT_VERSION -DgenerateBackupPoms=false
	git commit -am "releasing raidenjpa-$CLOSE_VERSION"
	git push

	mvn versions:commit
else
    echo "Script usage: ./super-release.sh 0.0.1 0.0.2-SNAPSHOT"
fi







