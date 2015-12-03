'use strict';

angular.module('spedycjacentralaApp')
	.controller('TransporterInfoDeleteController', function($scope, $modalInstance, entity, TransporterInfo) {

        $scope.transporterInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TransporterInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });