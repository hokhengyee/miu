/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ForumRoomMessageUpdateComponent } from 'app/entities/forum-room-message/forum-room-message-update.component';
import { ForumRoomMessageService } from 'app/entities/forum-room-message/forum-room-message.service';
import { ForumRoomMessage } from 'app/shared/model/forum-room-message.model';

describe('Component Tests', () => {
    describe('ForumRoomMessage Management Update Component', () => {
        let comp: ForumRoomMessageUpdateComponent;
        let fixture: ComponentFixture<ForumRoomMessageUpdateComponent>;
        let service: ForumRoomMessageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ForumRoomMessageUpdateComponent]
            })
                .overrideTemplate(ForumRoomMessageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ForumRoomMessageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForumRoomMessageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ForumRoomMessage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.forumRoomMessage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ForumRoomMessage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.forumRoomMessage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
