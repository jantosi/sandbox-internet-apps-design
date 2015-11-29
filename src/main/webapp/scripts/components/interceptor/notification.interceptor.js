 'use strict';

angular.module('spedycjaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-spedycjaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-spedycjaApp-params')});
                }
                return response;
            }
        };
    });
