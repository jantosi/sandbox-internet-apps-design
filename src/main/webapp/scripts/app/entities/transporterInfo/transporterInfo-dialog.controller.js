'use strict';

angular.module('spedycjacentralaApp').controller('TransporterInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TransporterInfo',
        function($scope, $stateParams, $modalInstance, entity, TransporterInfo) {

        $scope.transporterInfo = entity;
        $scope.load = function(id) {
            TransporterInfo.get({id : id}, function(result) {
                $scope.transporterInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('spedycjacentralaApp:transporterInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.transporterInfo.id != null) {
                TransporterInfo.update($scope.transporterInfo, onSaveSuccess, onSaveError);
            } else {
                TransporterInfo.save($scope.transporterInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
