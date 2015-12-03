'use strict';

angular.module('spedycjacentralaApp')
    .controller('EmployeePersonalDataController', function ($scope, $state, $modal, EmployeePersonalData) {
      
        $scope.employeePersonalDatas = [];
        $scope.loadAll = function() {
            EmployeePersonalData.query(function(result) {
               $scope.employeePersonalDatas = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employeePersonalData = {
                startDate: null,
                endDate: null,
                firstName: null,
                lastName: null,
                address: null,
                id: null
            };
        };
    });
