'use strict';

angular.module('spedycjacentralaApp')
    .controller('DepartmentController', function ($scope, $state, $modal, Department, ParseLinks) {
      
        $scope.departments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Department.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.departments = result;
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
            $scope.department = {
                name: null,
                address: null,
                city: null,
                id: null
            };
        };
    });
