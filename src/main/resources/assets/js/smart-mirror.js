'use strict';

require.config({
    baseUrl: 'js/',
    paths: {
        jquery: 'vendor/jquery/dist/jquery',
        moment: 'vendor/moment/moment'
    }
});

define([
    'jquery',
    'moment'
], function ($, moment) {

    var version = null;

    function isGoodService(tubeLine) {
        return tubeLine.statusSeverity === 10;
    }

    function parseTubeLine(tubeLine) {
        var $tubeLine = $('.' + tubeLine.id);
        if (!tubeLine.lineStatuses.some(isGoodService)) {
            $tubeLine.addClass('warning');
        } else {
            $tubeLine.removeClass('warning');
        }
    }

    function parsingCalendarEvents(event) {
        var time = moment(event.date).format('h:mm a');
        var $event = $('<li class="event"></li>').text(time + ' - ' + event.summary);
        $('.calendar ul').append($event)
    }

    function getEvents() {
        $.get('/application/calendar/events').done(function (data) {
            $('.calendar ul').empty();
            data.map(parsingCalendarEvents);

            $('.error').removeClass('eventsError');
            setTimeout(getEvents, 60000);
        }).error(function () {
            $('.error').addClass('eventsError');
            setTimeout(getEvents, 60000);
        });
    }

    function getTube() {
        $.get('/application/travel/tube').done(function (data) {
            data.map(parseTubeLine);
            setTimeout(getTube, 60000);

            $('.error').removeClass('tubeError');
        }).error(function () {
            $('.error').addClass('tubeError');
            setTimeout(getTube, 60000);
        });
    }

    function setTime() {
        var now = moment();
        var date = now.format('ddd, MMM Do YYYY');
        var time = now.format('h:mm:ss a');

        $('#date').text(date);
        $('#time').text(time);
        setTimeout(setTime, 1000);
    }

    function ensureLatestVersion() {
        $.get('/application/version').done(function (data) {

            if (version == null) {
                version = data;
            } else if (version != data) {
                location.reload();
            }

            $('.error').removeClass('versionError');
            setTimeout(ensureLatestVersion, 300000);
        }).error(function () {

            $('.error').addClass('versionError');
            setTimeout(ensureLatestVersion, 300000);
        });
    }

    ensureLatestVersion();
    setTime();
    getEvents();
    getTube();

});
