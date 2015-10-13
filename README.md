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

*   `./src/main/deploy` gives you a rough idea of where files should go for a deployment to a RaspberryPi:
  *   Init script must be copied to `/etc/init.d/smart-mirror` (and give it the correct permissions -> 0755)
  *   YAML configuration should be copied to `/etc/smart-mirror.yml`
  *   Application should be copied to `/opt/smart-mirror/smart-mirror.jar`
*   Now you can execute `sudo service smart-mirror start` and the app will spin up
* Do you want it to start up automagically on boot? Execute `sudo update-rc.d smart-mirror defaults`
*   Logs can be found at `/var/log/smart-mirror.log` and `/var/log/smart-mirror.err`
*   If you are using the SnapCI build pipeline, you can automatically upgrade the service when a new artifact is created:
  *   Copy `get_latest.sh` script to ``/opt/smart-mirror/get_latesh.sh`
  *   Execute `sudo crontab -e`
  *   Add `*/5 * * * * /opt/smart-mirror/get_latest.sh {username} {api_key} >> /var/log/smart-mirror.cron.yml 2>&1`

### Still to do

*   cron job to turn screen on/off in morning
*   JS to refresh page if version out of date
*   Start browser fullscreen on boot