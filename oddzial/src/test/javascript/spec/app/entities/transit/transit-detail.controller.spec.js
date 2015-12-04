'use strict';

describe('Transit Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTransit, MockEmployee, MockTransporter, MockDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTransit = jasmine.createSpy('MockTransit');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockTransporter = jasmine.createSpy('MockTransporter');
        MockDepartment = jasmine.createSpy('MockDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Transit': MockTransit,
            'Employee': MockEmployee,
            'Transporter': MockTransporter,
            'Department': MockDepartment
        };
        createController = function() {
            $injector.get('$controller')("TransitDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'spedycjaoddzialApp:transitUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
