# Smart Mirror

[![Build Status](https://snap-ci.com/ChrisSSocha/Smart-Mirror/branch/master/build_image)](https://snap-ci.com/ChrisSSocha/Smart-Mirror/branch/master)

## Installation

You will need:

*   Java 8
*   npm (which is installed with [node.js](https://nodejs.org/))

## Running the application

Run the following:

1.  `npm install -g bower`
2.  `bower install`
3.  `./gradlew compassCompile run`

The application should now be on [http://localhost:9098](http://localhost:9098)

## Installing on RaspberryPi

__ WORK IN PROGRESS __

*   Copy ``./scripts/init.d/smart-mirror` to `/etc/init.d/smart-mirror` (and git it the correct permissions -> 0755)
*   Copy `smart-mirror.jar` to `/opt/smart-mirror/`
*   Copy `smart-mirror.yml` to `/etc/`
*   Execute `sudo service smart-mirror start` and the app will spin up
  *   Errors? The logs can be found at `/var/log/smart-mirror.log` and `/var/log/smart-mirror.err`
* Do you want it to start up automagically on boot? Execute `sudo update-rc.d smart-mirror defaults`

### Still to do

*   cron job to turn screen on/off in morning
*   cron job to poll SnapCI for updated artifacts (and restart app)
*   JS to refresh page if version out of date
*   Start browser fullscreen on boot