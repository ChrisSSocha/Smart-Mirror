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
3.  `./gradlew run`

The application should now be on [http://localhost:9098](http://localhost:9098)

## Installing on RaspberryPi

`./src/main/deploy` gives you a rough idea of where files should go for a deployment to a RaspberryPi:

*   Init script must be copied to `/etc/init.d/smart-mirror` (and give it the correct permissions -> 0755)
*   YAML configuration should be copied to `/etc/smart-mirror.yml`
*   Application should be copied to `/opt/smart-mirror/smart-mirror.jar`

Now you can execute `sudo service smart-mirror start` and the app will spin up

### I want smart-mirror to start on boot!

    sudo update-rc.d smart-mirror defaults

### Where can i find logs?

Logs can be found at `/var/log/smart-mirror.log` and `/var/log/smart-mirror.err`

### Does smart-mirror automagically update itself?

Yes...ish

I use [SnapCI](https://snap-ci.com/) as my automated build pipeline. It picks up code changed from GitHub, runs the tests and generates build artifact on completion. I've written a small bash script which polls the SnapCI API to update the JAR if there are any changes

*   Copy `get_latest.sh` script to ``/opt/smart-mirror/get_latesh.sh`
*   Execute `sudo crontab -e`
*   Add `*/5 * * * * /opt/smart-mirror/get_latest.sh {username} {api_key} >> /var/log/smart-mirror.cron.log 2>&1`

### I want smart-mirror to turn on/off depending on the time of day!

You're in luck!

*   Copy the `turn_on_screen.sh` and `turn_off_screen.sh` to `/opt/smart-mirror/`
*   Execute `sudo crontab -e`
*   Add `0 7 * * * /opt/smart-mirror/turn_on_screen.sh >> /var/log/screen.cron.log 2>&1` to turn on at 7am
*   Add `30 8 * * * /opt/smart-mirror/turn_off_screen.sh >> /var/log/screen.cron.log 2>&1` to turn off at 8.30am

Source: [the-martins.org](http://the-martins.org/?p=81)

#### Awesome, my screen turns itself off! But how can i turn it back on manually?

Open `~/.config/openbox/lxde-rc.xml` and add the following to the `<keyboard>` section

    <keybind key=”A-C-S-1″>
      <action name=”Execute>
        <command>/opt/smart-mirror/turn_on_screen.sh</command>
      </action>
    </keybind>
    <keybind key=”A-C-S-0″>
      <action name=”Execute>
        <command>/opt/smart-mirror/turn_off_screen.sh</command>
      </action>
    </keybind>

Unlike crontab which runs as root, this will run as your user, so you'll need to ensure we can execute a few executables as your user

    chmod u+s /opt/vc/bin/tvservice
    chmod u+s /bin/chvt

Now You can turn your screen off with Alt-Control-Shift-1 and off with Alt-Control-Shift-0

Source: [cagewebdev.com](http://cagewebdev.com/index.php/raspberry-pi-binding-actions-to-hotkeys/)

### My screensaver blanks the screen :-(

`apt-get install x11-xserver-utils`

Edit `/etc/xdg/lxsession/LXDE-pi/autostart` and add these three lines

    @xset s off
    @xset -dpms
    @xset s noblank

Log out, log in, verify it's working with

`xset -q`

Source: [Stack Exchange](http://raspberrypi.stackexchange.com/questions/2059/disable-screen-blanking-in-x-windows-on-raspbian#5145)

## The screen resolution looks off!

https://www.raspberrypi.org/forums/viewtopic.php?f=29&t=24679

## Still to do

*   Start browser fullscreen on boot