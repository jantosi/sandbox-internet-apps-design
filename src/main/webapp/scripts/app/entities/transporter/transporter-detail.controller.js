'use strict';

angular.module('spedycjaApp')
    .controller('TransporterDetailController', function ($scope, $rootScope, $stateParams, entity, Transporter, Transit) {
        $scope.transporter = entity;
        $scope.load = function (id) {
            Transporter.get({id: id}, function(result) {
                $scope.transporter = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjaApp:transporterUpdate', function(event, result) {
            $scope.transporter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
