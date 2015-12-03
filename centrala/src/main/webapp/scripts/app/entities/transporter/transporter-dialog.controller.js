'use strict';

angular.module('spedycjacentralaApp').controller('TransporterDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Transporter', 'Department', 'TransporterInfo',
        function($scope, $stateParams, $modalInstance, $q, entity, Transporter, Department, TransporterInfo) {

        $scope.transporter = entity;
        $scope.departments = Department.query();
        $scope.transporterinfos = TransporterInfo.query({filter: 'transporter-is-null'});
        $q.all([$scope.transporter.$promise, $scope.transporterinfos.$promise]).then(function() {
            if (!$scope.transporter.transporterInfo || !$scope.transporter.transporterInfo.id) {
                return $q.reject();
            }
            return TransporterInfo.get({id : $scope.transporter.transporterInfo.id}).$promise;
        }).then(function(transporterInfo) {
            $scope.transporterinfos.push(transporterInfo);
        });
        $scope.load = function(id) {
            Transporter.get({id : id}, function(result) {
                $scope.transporter = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('spedycjacentralaApp:transporterUpdate', result);
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
