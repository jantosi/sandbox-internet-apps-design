'use strict';

angular.module('spedycjacentralaApp')
    .factory('Transporter', function ($resource, DateUtils) {
        return $resource('api/transporters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.purchaseTime = DateUtils.convertDateTimeFromServer(data.purchaseTime);
                    data.withdrawalTime = DateUtils.convertDateTimeFromServer(data.withdrawalTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
