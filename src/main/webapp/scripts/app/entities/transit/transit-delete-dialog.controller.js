'use strict';

angular.module('spedycjaApp')
	.controller('TransitDeleteController', function($scope, $modalInstance, entity, Transit) {

        $scope.transit = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Transit.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });