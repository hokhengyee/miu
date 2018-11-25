'use strict';

describe('Controller Tests', function() {

    describe('CourseAccess Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCourseAccess, MockUser, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCourseAccess = jasmine.createSpy('MockCourseAccess');
            MockUser = jasmine.createSpy('MockUser');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CourseAccess': MockCourseAccess,
                'User': MockUser,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("CourseAccessDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:courseAccessUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
