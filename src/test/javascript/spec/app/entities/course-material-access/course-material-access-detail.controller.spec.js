'use strict';

describe('Controller Tests', function() {

    describe('CourseMaterialAccess Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCourseMaterialAccess, MockCourse, MockCourseMaterial;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCourseMaterialAccess = jasmine.createSpy('MockCourseMaterialAccess');
            MockCourse = jasmine.createSpy('MockCourse');
            MockCourseMaterial = jasmine.createSpy('MockCourseMaterial');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CourseMaterialAccess': MockCourseMaterialAccess,
                'Course': MockCourse,
                'CourseMaterial': MockCourseMaterial
            };
            createController = function() {
                $injector.get('$controller')("CourseMaterialAccessDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:courseMaterialAccessUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
