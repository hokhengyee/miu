/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ForumRoomDetailComponent } from 'app/entities/forum-room/forum-room-detail.component';
import { ForumRoom } from 'app/shared/model/forum-room.model';

describe('Component Tests', () => {
    describe('ForumRoom Management Detail Component', () => {
        let comp: ForumRoomDetailComponent;
        let fixture: ComponentFixture<ForumRoomDetailComponent>;
        const route = ({ data: of({ forumRoom: new ForumRoom(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ForumRoomDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ForumRoomDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ForumRoomDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.forumRoom).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
