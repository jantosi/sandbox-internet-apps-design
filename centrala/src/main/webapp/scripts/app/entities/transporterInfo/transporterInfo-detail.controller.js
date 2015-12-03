'use strict';

angular.module('spedycjacentralaApp')
    .controller('TransporterInfoDetailController', function ($scope, $rootScope, $stateParams, entity, TransporterInfo) {
        $scope.transporterInfo = entity;
        $scope.load = function (id) {
            TransporterInfo.get({id: id}, function(result) {
                $scope.transporterInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjacentralaApp:transporterInfoUpdate', function(event, result) {
            $scope.transporterInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
