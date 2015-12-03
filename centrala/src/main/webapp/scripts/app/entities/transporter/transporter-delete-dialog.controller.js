'use strict';

angular.module('spedycjacentralaApp')
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