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

    console.log($('body'));

});
