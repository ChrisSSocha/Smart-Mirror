'use strict';

require.config({
    baseUrl: 'js/',
    paths: {
        jquery: 'vendor/jquery/dist/jquery',
        moment: 'vendor/moment/moment',
    }
});

define([
    'jquery',
    'moment'
], function ($, moment) {

    function isGoodService(tubeLine){
        return tubeLine.statusSeverity === 10;
    }

    function parseTubeLine(tubeLine){
        var $tubeLine = $('.' + tubeLine.id);
        if (!tubeLine.lineStatuses.some(isGoodService)){
            $tubeLine.addClass('warning');
        } else {
            $tubeLine.removeClass('warning');
        }
    }

    function parsingCalendarEvents(event){
        var time = moment(event.date).format('h:mm:ss a');
        var $event = $('<li class="event"></li>').text(time + ' - ' + event.summary);
        $('.calendar ul').append($event)
    }

    function getEvents(){
        $.get('/application/calendar/events').done(function(data) {
            data.map(parsingCalendarEvents);
            //setTimeout(getEvents,5000);
        }).error(function(){
            //setTimeout(getEvents,5000);
        });
    }

    function getTube(){
        $.get('/application/travel/tube').done(function(data) {
            data.map(parseTubeLine);
            //setTimeout(getTube,5000);
        }).error(function(){
            //setTimeout(getTube,5000);
        });
    }

    function setTime(){
        var now = moment();
        var date = now.format('ddd, MMM Do YYYY');
        var time = now.format('h:mm:ss a');

        $('#date').text(date);
        $('#time').text(time);
        setTimeout(setTime, 1000);
    }

    setTime();
    getEvents();
    getTube();

});
