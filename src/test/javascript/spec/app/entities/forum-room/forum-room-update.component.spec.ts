/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ForumRoomUpdateComponent } from 'app/entities/forum-room/forum-room-update.component';
import { ForumRoomService } from 'app/entities/forum-room/forum-room.service';
import { ForumRoom } from 'app/shared/model/forum-room.model';

describe('Component Tests', () => {
    describe('ForumRoom Management Update Component', () => {
        let comp: ForumRoomUpdateComponent;
        let fixture: ComponentFixture<ForumRoomUpdateComponent>;
        let service: ForumRoomService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ForumRoomUpdateComponent]
            })
                .overrideTemplate(ForumRoomUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ForumRoomUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForumRoomService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ForumRoom(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.forumRoom = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ForumRoom();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.forumRoom = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
