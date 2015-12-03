'use strict';

angular.module('spedycjacentralaApp')
	.controller('EmployeePersonalDataDeleteController', function($scope, $modalInstance, entity, EmployeePersonalData) {

        $scope.employeePersonalData = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            EmployeePersonalData.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });