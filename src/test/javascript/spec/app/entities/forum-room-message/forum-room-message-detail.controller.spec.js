'use strict';

describe('Controller Tests', function() {

    describe('ForumRoomMessage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockForumRoomMessage, MockForumRoom, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockForumRoomMessage = jasmine.createSpy('MockForumRoomMessage');
            MockForumRoom = jasmine.createSpy('MockForumRoom');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ForumRoomMessage': MockForumRoomMessage,
                'ForumRoom': MockForumRoom,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ForumRoomMessageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'miuApp:forumRoomMessageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
