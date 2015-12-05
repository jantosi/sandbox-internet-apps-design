'use strict';

angular.module('spedycjaoddzialApp')
    .controller('DepartmentController', function ($scope, $state, $modal, Department) {
      
        $scope.departments = [];
        $scope.loadAll = function() {
            Department.query(function(result) {
               $scope.departments = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.department = {
                departmentName: null,
                address: null,
                id: null
            };
        };
    });
