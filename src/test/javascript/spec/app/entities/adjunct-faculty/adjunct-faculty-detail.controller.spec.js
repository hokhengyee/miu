'use strict';

describe('Controller Tests', function() {

    describe('AdjunctFaculty Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAdjunctFaculty, MockLecturerProfile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAdjunctFaculty = jasmine.createSpy('MockAdjunctFaculty');
            MockLecturerProfile = jasmine.createSpy('MockLecturerProfile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AdjunctFaculty': MockAdjunctFaculty,
                'LecturerProfile': MockLecturerProfile
            };
            createController = function() {
                $injector.get('$controller')("AdjunctFacultyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:adjunctFacultyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
