'use strict';

angular.module('spedycjaoddzialApp')
    .controller('MainController', function ($scope, Principal, $http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

       $scope.synchronize = function synchronize() {
        console.log("CALLING SYNCHRONIZE METHOD");
        $http.post('dbsync/dbsync/headquarters').
       	        success(function(data) {
       	            $scope.greeting = data;
       	        })
       }
    });



