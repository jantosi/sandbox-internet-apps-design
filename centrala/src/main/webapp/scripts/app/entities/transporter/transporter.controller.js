'use strict';

angular.module('spedycjacentralaApp')
    .controller('TransporterController', function ($scope, $state, $modal, Transporter, ParseLinks) {
      
        $scope.transporters = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Transporter.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.transporters = result;
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
            $scope.transporter = {
                purchaseTime: null,
                withdrawalTime: null,
                name: null,
                id: null
            };
        };
    });
