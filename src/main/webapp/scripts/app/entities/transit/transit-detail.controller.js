'use strict';

angular.module('spedycjaApp')
    .controller('TransitDetailController', function ($scope, $rootScope, $stateParams, entity, Transit, Employee, Transporter) {
        $scope.transit = entity;
        $scope.load = function (id) {
            Transit.get({id: id}, function(result) {
                $scope.transit = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjaApp:transitUpdate', function(event, result) {
            $scope.transit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
