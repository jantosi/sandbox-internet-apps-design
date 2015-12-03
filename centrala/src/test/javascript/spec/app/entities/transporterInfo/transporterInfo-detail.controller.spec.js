'use strict';

describe('TransporterInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTransporterInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTransporterInfo = jasmine.createSpy('MockTransporterInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TransporterInfo': MockTransporterInfo
        };
        createController = function() {
            $injector.get('$controller')("TransporterInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'spedycjacentralaApp:transporterInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
