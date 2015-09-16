'use strict';

require.config({
    baseUrl: 'js/',
    paths: {
        jquery: 'vendor/jquery/dist/jquery'
    }
});

define([
    'jquery'
], function ($) {

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

    function getEvents(){
        $.get('/application/calendar/events').done(function(data) {
            console.log(data);
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

    getEvents();
    getTube();

});
