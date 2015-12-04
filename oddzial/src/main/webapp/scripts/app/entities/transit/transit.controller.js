'use strict';

angular.module('spedycjaoddzialApp')
    .controller('TransitController', function ($scope, $state, $modal, Transit) {
      
        $scope.transits = [];
        $scope.loadAll = function() {
            Transit.query(function(result) {
               $scope.transits = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.transit = {
                type: null,
                startTime: null,
                endTime: null,
                id: null
            };
        };
    });
