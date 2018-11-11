'use strict';

describe('Controller Tests', function() {

    describe('StudentProfile Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentProfile, MockSalutation, MockGender, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentProfile = jasmine.createSpy('MockStudentProfile');
            MockSalutation = jasmine.createSpy('MockSalutation');
            MockGender = jasmine.createSpy('MockGender');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentProfile': MockStudentProfile,
                'Salutation': MockSalutation,
                'Gender': MockGender,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("StudentProfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:studentProfileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
