'use strict';

describe('EmployeePersonalData Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeePersonalData;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeePersonalData = jasmine.createSpy('MockEmployeePersonalData');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeePersonalData': MockEmployeePersonalData
        };
        createController = function() {
            $injector.get('$controller')("EmployeePersonalDataDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'spedycjacentralaApp:employeePersonalDataUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
