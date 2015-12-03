'use strict';

angular.module('spedycjacentralaApp')
    .controller('EmployeePersonalDataDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeePersonalData) {
        $scope.employeePersonalData = entity;
        $scope.load = function (id) {
            EmployeePersonalData.get({id: id}, function(result) {
                $scope.employeePersonalData = result;
            });
        };
        var unsubscribe = $rootScope.$on('spedycjacentralaApp:employeePersonalDataUpdate', function(event, result) {
            $scope.employeePersonalData = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
