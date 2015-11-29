'use strict';

describe('Transporter Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTransporter, MockTransit;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTransporter = jasmine.createSpy('MockTransporter');
        MockTransit = jasmine.createSpy('MockTransit');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Transporter': MockTransporter,
            'Transit': MockTransit
        };
        createController = function() {
            $injector.get('$controller')("TransporterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'spedycjaApp:transporterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
