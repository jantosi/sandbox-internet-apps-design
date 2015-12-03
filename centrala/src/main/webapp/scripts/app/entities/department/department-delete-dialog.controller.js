'use strict';

angular.module('spedycjacentralaApp')
	.controller('DepartmentDeleteController', function($scope, $modalInstance, entity, Department) {

        $scope.department = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Department.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });