/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { ForumRoomMessageDeleteDialogComponent } from 'app/entities/forum-room-message/forum-room-message-delete-dialog.component';
import { ForumRoomMessageService } from 'app/entities/forum-room-message/forum-room-message.service';

describe('Component Tests', () => {
    describe('ForumRoomMessage Management Delete Component', () => {
        let comp: ForumRoomMessageDeleteDialogComponent;
        let fixture: ComponentFixture<ForumRoomMessageDeleteDialogComponent>;
        let service: ForumRoomMessageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ForumRoomMessageDeleteDialogComponent]
            })
                .overrideTemplate(ForumRoomMessageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ForumRoomMessageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForumRoomMessageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
