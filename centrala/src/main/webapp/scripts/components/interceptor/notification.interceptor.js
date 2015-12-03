 'use strict';

angular.module('spedycjacentralaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-spedycjacentralaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-spedycjacentralaApp-params')});
                }
                return response;
            }
        };
    });
