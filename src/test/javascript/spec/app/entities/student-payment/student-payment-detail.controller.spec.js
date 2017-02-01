'use strict';

describe('Controller Tests', function() {

    describe('StudentPayment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentPayment, MockUser, MockCourse, MockPaymentType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentPayment = jasmine.createSpy('MockStudentPayment');
            MockUser = jasmine.createSpy('MockUser');
            MockCourse = jasmine.createSpy('MockCourse');
            MockPaymentType = jasmine.createSpy('MockPaymentType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentPayment': MockStudentPayment,
                'User': MockUser,
                'Course': MockCourse,
                'PaymentType': MockPaymentType
            };
            createController = function() {
                $injector.get('$controller')("StudentPaymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:studentPaymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
