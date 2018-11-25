'use strict';

describe('Controller Tests', function() {

    describe('StudentModuleResult Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentModuleResult, MockUser, MockModule;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentModuleResult = jasmine.createSpy('MockStudentModuleResult');
            MockUser = jasmine.createSpy('MockUser');
            MockModule = jasmine.createSpy('MockModule');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentModuleResult': MockStudentModuleResult,
                'User': MockUser,
                'Module': MockModule
            };
            createController = function() {
                $injector.get('$controller')("StudentModuleResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:studentModuleResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
