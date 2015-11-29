'use strict';

angular.module('spedycjaApp')
    .factory('Transporter', function ($resource, DateUtils) {
        return $resource('api/transporters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
