/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentOtherResultDetailComponent } from 'app/entities/student-other-result/student-other-result-detail.component';
import { StudentOtherResult } from 'app/shared/model/student-other-result.model';

describe('Component Tests', () => {
    describe('StudentOtherResult Management Detail Component', () => {
        let comp: StudentOtherResultDetailComponent;
        let fixture: ComponentFixture<StudentOtherResultDetailComponent>;
        const route = ({ data: of({ studentOtherResult: new StudentOtherResult(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentOtherResultDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentOtherResultDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentOtherResultDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentOtherResult).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
