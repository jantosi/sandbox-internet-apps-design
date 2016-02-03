'use strict';

angular.module('spedycjaoddzialApp')
    .controller('MainController', function ($scope, Principal, $http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

       $scope.synchronizeUsers = function synchronize() {
        console.log("CALLING SYNCHRONIZE USERS METHOD");
        $http.post('dbsync/users').
       	        success(function(data) {
       	            $scope.greeting = data;
       	        })
       }

      $scope.synchronizeBusinessData = function synchronize() {
       console.log("CALLING SYNCHRONIZE BUSINESS DATA METHOD");
       $http.post('dbsync/businessdata').
                success(function(data) {
                    $scope.greeting = data;
                })
      }
    });




