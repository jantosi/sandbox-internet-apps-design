'use strict';

angular.module('spedycjaApp').controller('TransporterDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Transporter', 'Transit',
        function($scope, $stateParams, $modalInstance, entity, Transporter, Transit) {

        $scope.transporter = entity;
        $scope.transits = Transit.query();
        $scope.load = function(id) {
            Transporter.get({id : id}, function(result) {
                $scope.transporter = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('spedycjaApp:transporterUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.transporter.id != null) {
                Transporter.update($scope.transporter, onSaveSuccess, onSaveError);
            } else {
                Transporter.save($scope.transporter, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
