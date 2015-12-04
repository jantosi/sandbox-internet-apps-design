'use strict';

angular.module('spedycjaoddzialApp')
    .controller('TransporterDetailController', function ($scope, $rootScope, $stateParams, entity, Transporter) {
        $scope.transporter = entity;
        $scope.load = function (id) {
            Transporter.get({id: id}, function(result) {
                $scope.transporter = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjaoddzialApp:transporterUpdate', function(event, result) {
            $scope.transporter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
