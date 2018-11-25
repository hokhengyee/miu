/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { LecturerProfileDetailComponent } from 'app/entities/lecturer-profile/lecturer-profile-detail.component';
import { LecturerProfile } from 'app/shared/model/lecturer-profile.model';

describe('Component Tests', () => {
    describe('LecturerProfile Management Detail Component', () => {
        let comp: LecturerProfileDetailComponent;
        let fixture: ComponentFixture<LecturerProfileDetailComponent>;
        const route = ({ data: of({ lecturerProfile: new LecturerProfile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [LecturerProfileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LecturerProfileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LecturerProfileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lecturerProfile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
