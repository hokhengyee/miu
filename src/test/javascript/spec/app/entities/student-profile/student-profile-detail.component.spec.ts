/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentProfileDetailComponent } from 'app/entities/student-profile/student-profile-detail.component';
import { StudentProfile } from 'app/shared/model/student-profile.model';

describe('Component Tests', () => {
    describe('StudentProfile Management Detail Component', () => {
        let comp: StudentProfileDetailComponent;
        let fixture: ComponentFixture<StudentProfileDetailComponent>;
        const route = ({ data: of({ studentProfile: new StudentProfile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentProfileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentProfileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentProfileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentProfile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
