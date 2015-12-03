'use strict';

angular.module('spedycjacentralaApp').controller('EmployeePersonalDataDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'EmployeePersonalData',
        function($scope, $stateParams, $modalInstance, entity, EmployeePersonalData) {

        $scope.employeePersonalData = entity;
        $scope.load = function(id) {
            EmployeePersonalData.get({id : id}, function(result) {
                $scope.employeePersonalData = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('spedycjacentralaApp:employeePersonalDataUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employeePersonalData.id != null) {
                EmployeePersonalData.update($scope.employeePersonalData, onSaveSuccess, onSaveError);
            } else {
                EmployeePersonalData.save($scope.employeePersonalData, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
