'use strict';

angular.module('spedycjaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('transit', {
                parent: 'entity',
                url: '/transits',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjaApp.transit.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transit/transits.html',
                        controller: 'TransitController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('transit');
                        $translatePartialLoader.addPart('transitType');
                        $translatePartialLoader.addPart('transitStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('transit.detail', {
                parent: 'entity',
                url: '/transit/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'spedycjaApp.transit.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/transit/transit-detail.html',
                        controller: 'TransitDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('transit');
                        $translatePartialLoader.addPart('transitType');
                        $translatePartialLoader.addPart('transitStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Transit', function($stateParams, Transit) {
                        return Transit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('transit.new', {
                parent: 'transit',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transit/transit-dialog.html',
                        controller: 'TransitDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    status: null,
                                    startTime: null,
                                    endTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('transit', null, { reload: true });
                    }, function() {
                        $state.go('transit');
                    })
                }]
            })
            .state('transit.edit', {
                parent: 'transit',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transit/transit-dialog.html',
                        controller: 'TransitDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Transit', function(Transit) {
                                return Transit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('transit.delete', {
                parent: 'transit',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/transit/transit-delete-dialog.html',
                        controller: 'TransitDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Transit', function(Transit) {
                                return Transit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('transit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
