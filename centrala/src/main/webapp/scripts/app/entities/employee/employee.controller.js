'use strict';

angular.module('spedycjacentralaApp')
    .controller('EmployeeController', function ($scope, $state, $modal, Employee, ParseLinks) {
      
        $scope.employees = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Employee.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employees = result;
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
            $scope.employee = {
                login: null,
                position: null,
                id: null
            };
        };
    });
