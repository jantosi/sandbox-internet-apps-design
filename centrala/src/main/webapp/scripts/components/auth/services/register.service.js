'use strict';

angular.module('spedycjacentralaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


