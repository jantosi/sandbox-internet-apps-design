'use strict';

angular.module('spedycjacentralaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('transporterInfo', {
                parent: 'entity',
                url: '/transporterInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjacentralaApp.transporterInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transporterInfo/transporterInfos.html',
                        controller: 'TransporterInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('transporterInfo');
                        $translatePartialLoader.addPart('transporterType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('transporterInfo.detail', {
                parent: 'entity',
                url: '/transporterInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjacentralaApp.transporterInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transporterInfo/transporterInfo-detail.html',
                        controller: 'TransporterInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('transporterInfo');
                        $translatePartialLoader.addPart('transporterType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TransporterInfo', function($stateParams, TransporterInfo) {
                        return TransporterInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('transporterInfo.new', {
                parent: 'transporterInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transporterInfo/transporterInfo-dialog.html',
                        controller: 'TransporterInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    model: null,
                                    make: null,
                                    year: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('transporterInfo', null, { reload: true });
                    }, function() {
                        $state.go('transporterInfo');
                    })
                }]
            })
            .state('transporterInfo.edit', {
                parent: 'transporterInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transporterInfo/transporterInfo-dialog.html',
                        controller: 'TransporterInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TransporterInfo', function(TransporterInfo) {
                                return TransporterInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transporterInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('transporterInfo.delete', {
                parent: 'transporterInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transporterInfo/transporterInfo-delete-dialog.html',
                        controller: 'TransporterInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TransporterInfo', function(TransporterInfo) {
                                return TransporterInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transporterInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
