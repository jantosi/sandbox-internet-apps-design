'use strict';

angular.module('spedycjaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


