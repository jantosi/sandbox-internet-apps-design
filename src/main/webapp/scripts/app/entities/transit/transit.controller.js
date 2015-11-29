'use strict';

angular.module('spedycjaApp')
    .controller('TransitController', function ($scope, $state, $modal, Transit, ParseLinks) {
      
        $scope.transits = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Transit.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.transits = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.transit = {
                type: null,
                status: null,
                startTime: null,
                endTime: null,
                id: null
            };
        };
    });
