'use strict';

angular.module('spedycjaoddzialApp')
	.controller('TransporterDeleteController', function($scope, $modalInstance, entity, Transporter) {

        $scope.transporter = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Transporter.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });