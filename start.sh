#!/usr/bin/env sh

java -Dappname=niflheim -Dspring.profiles.active=$1 -jar ./niflheim.jar

