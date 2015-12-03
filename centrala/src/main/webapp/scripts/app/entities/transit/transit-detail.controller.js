'use strict';

angular.module('spedycjacentralaApp')
    .controller('TransitDetailController', function ($scope, $rootScope, $stateParams, entity, Transit, Employee, Transporter, Department) {
        $scope.transit = entity;
        $scope.load = function (id) {
            Transit.get({id: id}, function(result) {
                $scope.transit = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjacentralaApp:transitUpdate', function(event, result) {
            $scope.transit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
