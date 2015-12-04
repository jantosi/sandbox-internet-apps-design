'use strict';

angular.module('spedycjaoddzialApp')
    .controller('TransporterController', function ($scope, $state, $modal, Transporter) {
      
        $scope.transporters = [];
        $scope.loadAll = function() {
            Transporter.query(function(result) {
               $scope.transporters = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.transporter = {
                id: null
            };
        };
    });
