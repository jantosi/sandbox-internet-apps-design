'use strict';

angular.module('spedycjacentralaApp').controller('EmployeeDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Employee', 'Department', 'EmployeePersonalData',
        function($scope, $stateParams, $modalInstance, $q, entity, Employee, Department, EmployeePersonalData) {

        $scope.employee = entity;
        $scope.departments = Department.query();
        $scope.employeepersonaldatas = EmployeePersonalData.query({filter: 'employee-is-null'});
        $q.all([$scope.employee.$promise, $scope.employeepersonaldatas.$promise]).then(function() {
            if (!$scope.employee.employeePersonalData || !$scope.employee.employeePersonalData.id) {
                return $q.reject();
            }
            return EmployeePersonalData.get({id : $scope.employee.employeePersonalData.id}).$promise;
        }).then(function(employeePersonalData) {
            $scope.employeepersonaldatas.push(employeePersonalData);
        });
        $scope.load = function(id) {
            Employee.get({id : id}, function(result) {
                $scope.employee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('spedycjacentralaApp:employeeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employee.id != null) {
                Employee.update($scope.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save($scope.employee, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
