'use strict';

angular.module('spedycjacentralaApp')
    .factory('TransporterInfo', function ($resource, DateUtils) {
        return $resource('api/transporterInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.year = DateUtils.convertDateTimeFromServer(data.year);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
