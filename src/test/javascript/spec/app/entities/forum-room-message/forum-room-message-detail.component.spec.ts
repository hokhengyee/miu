/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ForumRoomMessageDetailComponent } from 'app/entities/forum-room-message/forum-room-message-detail.component';
import { ForumRoomMessage } from 'app/shared/model/forum-room-message.model';

describe('Component Tests', () => {
    describe('ForumRoomMessage Management Detail Component', () => {
        let comp: ForumRoomMessageDetailComponent;
        let fixture: ComponentFixture<ForumRoomMessageDetailComponent>;
        const route = ({ data: of({ forumRoomMessage: new ForumRoomMessage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ForumRoomMessageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ForumRoomMessageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ForumRoomMessageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.forumRoomMessage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
