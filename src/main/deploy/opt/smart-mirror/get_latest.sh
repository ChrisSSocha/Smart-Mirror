#!/bin/bash

USERNAME=${1:-$USERNAME}
API_KEY=${2:-$API_KEY}

set -e

function usage() {
  echo
  echo "$0 [username [password]]"
  exit 1
}

if [ -z "${USERNAME}" ]; then
  echo "Please set the USERNAME environment variable or provide it on the command line"
  usage
fi

if [ -z "${API_KEY}" ]; then
  echo "Please set the API_KEY environment variable or provide it on the command line"
  usage
fi

LATEST=`curl --silent -u $USERNAME:$API_KEY -X GET -H 'Accept: application/vnd.snap-ci.com.v1+json' https://api.snap-ci.com/project/ChrisSSocha/Smart-Mirror/branch/master/pipelines/latest --location`
COUNTER=`echo $LATEST | ruby -r json -e 'puts JSON.parse(STDIN.read)["counter"]'`
RESULT=`echo $LATEST | ruby -r json -e 'puts JSON.parse(STDIN.read)["result"]'`

if [ $RESULT = 'failed' ]
then
  echo "Latest build ($COUNTER) failed. Aborting"
  exit 0
fi

DOWNLOAD_URL=`echo $LATEST | ruby -r json -e 'puts JSON.parse(STDIN.read)["stages"][0]["workers"][0]["artifacts"][0]["download_url"]'`

function update {
  echo "Stopping Smart Mirror"
  /usr/sbin/service smart-mirror stop
  echo "Downloading $DOWNLOAD_URL"
  curl --silent -u $USERNAME:$API_KEY -X GET -H 'Accept: application/vnd.snap-ci.com.v1+json' $DOWNLOAD_URL --location -o /opt/smart-mirror/smart-mirror.jar
  echo $COUNTER > /opt/smart-mirror/latest_version
  echo "Starting Smart Mirror"
  /usr/sbin/service smart-mirror start
}

if [ -e /opt/smart-mirror/latest_version ]
then
  CURRENT=`cat /opt/smart-mirror/latest_version`
  if [ $COUNTER -gt $CURRENT ]
  then
  	echo "Version out of date. Current = $CURRENT. Latest = $COUNTER"
    update
  else
  	echo "Version already up to date"
  fi
else
  echo "First time install"
  update
fi