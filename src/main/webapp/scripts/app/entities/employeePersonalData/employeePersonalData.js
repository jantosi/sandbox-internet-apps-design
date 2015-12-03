'use strict';

angular.module('spedycjacentralaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeePersonalData', {
                parent: 'entity',
                url: '/employeePersonalDatas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjacentralaApp.employeePersonalData.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeePersonalData/employeePersonalDatas.html',
                        controller: 'EmployeePersonalDataController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeePersonalData');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeePersonalData.detail', {
                parent: 'entity',
                url: '/employeePersonalData/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjacentralaApp.employeePersonalData.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeePersonalData/employeePersonalData-detail.html',
                        controller: 'EmployeePersonalDataDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeePersonalData');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeePersonalData', function($stateParams, EmployeePersonalData) {
                        return EmployeePersonalData.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeePersonalData.new', {
                parent: 'employeePersonalData',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeePersonalData/employeePersonalData-dialog.html',
                        controller: 'EmployeePersonalDataDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    startDate: null,
                                    endDate: null,
                                    firstName: null,
                                    lastName: null,
                                    address: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employeePersonalData', null, { reload: true });
                    }, function() {
                        $state.go('employeePersonalData');
                    })
                }]
            })
            .state('employeePersonalData.edit', {
                parent: 'employeePersonalData',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeePersonalData/employeePersonalData-dialog.html',
                        controller: 'EmployeePersonalDataDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EmployeePersonalData', function(EmployeePersonalData) {
                                return EmployeePersonalData.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeePersonalData', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('employeePersonalData.delete', {
                parent: 'employeePersonalData',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeePersonalData/employeePersonalData-delete-dialog.html',
                        controller: 'EmployeePersonalDataDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['EmployeePersonalData', function(EmployeePersonalData) {
                                return EmployeePersonalData.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeePersonalData', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
