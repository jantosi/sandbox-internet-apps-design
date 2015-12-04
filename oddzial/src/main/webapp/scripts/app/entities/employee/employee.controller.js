'use strict';

angular.module('spedycjaoddzialApp')
    .controller('EmployeeController', function ($scope, $state, $modal, Employee) {
      
        $scope.employees = [];
        $scope.loadAll = function() {
            Employee.query(function(result) {
               $scope.employees = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employee = {
                login: null,
                password: null,
                id: null
            };
        };
    });
