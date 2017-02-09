'use strict';

describe('Controller Tests', function() {

    describe('LecturerProfile Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLecturerProfile, MockUser, MockSalutation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLecturerProfile = jasmine.createSpy('MockLecturerProfile');
            MockUser = jasmine.createSpy('MockUser');
            MockSalutation = jasmine.createSpy('MockSalutation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LecturerProfile': MockLecturerProfile,
                'User': MockUser,
                'Salutation': MockSalutation
            };
            createController = function() {
                $injector.get('$controller')("LecturerProfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:lecturerProfileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
