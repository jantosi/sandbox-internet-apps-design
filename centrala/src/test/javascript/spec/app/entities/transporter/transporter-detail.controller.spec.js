'use strict';

describe('Transporter Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTransporter, MockDepartment, MockTransporterInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTransporter = jasmine.createSpy('MockTransporter');
        MockDepartment = jasmine.createSpy('MockDepartment');
        MockTransporterInfo = jasmine.createSpy('MockTransporterInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Transporter': MockTransporter,
            'Department': MockDepartment,
            'TransporterInfo': MockTransporterInfo
        };
        createController = function() {
            $injector.get('$controller')("TransporterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'spedycjacentralaApp:transporterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
