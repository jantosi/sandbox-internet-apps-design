'use strict';

angular.module('spedycjacentralaApp')
    .controller('TransporterDetailController', function ($scope, $rootScope, $stateParams, entity, Transporter, Department, TransporterInfo) {
        $scope.transporter = entity;
        $scope.load = function (id) {
            Transporter.get({id: id}, function(result) {
                $scope.transporter = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjacentralaApp:transporterUpdate', function(event, result) {
            $scope.transporter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
