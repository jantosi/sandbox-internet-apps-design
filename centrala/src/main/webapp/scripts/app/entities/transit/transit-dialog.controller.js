'use strict';

angular.module('spedycjacentralaApp').controller('TransitDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Transit', 'Employee', 'Transporter', 'Department',
        function($scope, $stateParams, $modalInstance, entity, Transit, Employee, Transporter, Department) {

        $scope.transit = entity;
        $scope.employees = Employee.query();
        $scope.transporters = Transporter.query();
        $scope.departments = Department.query();
        $scope.load = function(id) {
            Transit.get({id : id}, function(result) {
                $scope.transit = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('spedycjacentralaApp:transitUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.transit.id != null) {
                Transit.update($scope.transit, onSaveSuccess, onSaveError);
            } else {
                Transit.save($scope.transit, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
