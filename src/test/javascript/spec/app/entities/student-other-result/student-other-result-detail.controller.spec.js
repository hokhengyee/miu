'use strict';

describe('Controller Tests', function() {

    describe('StudentOtherResult Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentOtherResult, MockCustomStudentReportType, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentOtherResult = jasmine.createSpy('MockStudentOtherResult');
            MockCustomStudentReportType = jasmine.createSpy('MockCustomStudentReportType');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentOtherResult': MockStudentOtherResult,
                'CustomStudentReportType': MockCustomStudentReportType,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("StudentOtherResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:studentOtherResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
