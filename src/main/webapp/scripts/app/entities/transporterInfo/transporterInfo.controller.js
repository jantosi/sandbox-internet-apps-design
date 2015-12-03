'use strict';

angular.module('spedycjacentralaApp')
    .controller('TransporterInfoController', function ($scope, $state, $modal, TransporterInfo, ParseLinks) {
      
        $scope.transporterInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TransporterInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.transporterInfos = result;
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
            $scope.transporterInfo = {
                type: null,
                model: null,
                make: null,
                year: null,
                id: null
            };
        };
    });
