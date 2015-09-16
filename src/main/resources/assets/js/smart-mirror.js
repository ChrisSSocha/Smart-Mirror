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

    function getEvents(){
        $.get('/application/calendar/events', function(data) {
            console.log(data);
            //setTimeout(getEvents,5000);
        });
    }

    function getTube(){
        $.get('/application/travel/tube', function(data) {
            console.log(data);
            //setTimeout(getTube,5000);
        });
    }

    getEvents();
    getTube();

});
