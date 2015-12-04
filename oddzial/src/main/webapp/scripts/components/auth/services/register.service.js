'use strict';

angular.module('spedycjaoddzialApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


