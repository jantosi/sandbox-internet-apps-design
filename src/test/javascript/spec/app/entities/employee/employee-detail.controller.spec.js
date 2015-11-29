'use strict';

describe('Employee Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployee, MockTransit;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockTransit = jasmine.createSpy('MockTransit');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Employee': MockEmployee,
            'Transit': MockTransit
        };
        createController = function() {
            $injector.get('$controller')("EmployeeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'spedycjaApp:employeeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
