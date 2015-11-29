'use strict';

angular.module('spedycjaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('transporter', {
                parent: 'entity',
                url: '/transporters',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjaApp.transporter.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transporter/transporters.html',
                        controller: 'TransporterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('transporter');
                        $translatePartialLoader.addPart('transporterType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('transporter.detail', {
                parent: 'entity',
                url: '/transporter/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjaApp.transporter.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transporter/transporter-detail.html',
                        controller: 'TransporterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('transporter');
                        $translatePartialLoader.addPart('transporterType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Transporter', function($stateParams, Transporter) {
                        return Transporter.get({id : $stateParams.id});
                    }]
                }
            })
            .state('transporter.new', {
                parent: 'transporter',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transporter/transporter-dialog.html',
                        controller: 'TransporterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    model: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('transporter', null, { reload: true });
                    }, function() {
                        $state.go('transporter');
                    })
                }]
            })
            .state('transporter.edit', {
                parent: 'transporter',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transporter/transporter-dialog.html',
                        controller: 'TransporterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Transporter', function(Transporter) {
                                return Transporter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transporter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('transporter.delete', {
                parent: 'transporter',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transporter/transporter-delete-dialog.html',
                        controller: 'TransporterDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Transporter', function(Transporter) {
                                return Transporter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transporter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
