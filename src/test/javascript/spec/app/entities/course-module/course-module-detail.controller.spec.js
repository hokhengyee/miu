'use strict';

describe('Controller Tests', function() {

    describe('CourseModule Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCourseModule, MockCourse, MockModule;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCourseModule = jasmine.createSpy('MockCourseModule');
            MockCourse = jasmine.createSpy('MockCourse');
            MockModule = jasmine.createSpy('MockModule');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CourseModule': MockCourseModule,
                'Course': MockCourse,
                'Module': MockModule
            };
            createController = function() {
                $injector.get('$controller')("CourseModuleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:courseModuleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
