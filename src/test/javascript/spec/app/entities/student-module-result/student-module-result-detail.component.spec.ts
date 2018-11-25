/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentModuleResultDetailComponent } from 'app/entities/student-module-result/student-module-result-detail.component';
import { StudentModuleResult } from 'app/shared/model/student-module-result.model';

describe('Component Tests', () => {
    describe('StudentModuleResult Management Detail Component', () => {
        let comp: StudentModuleResultDetailComponent;
        let fixture: ComponentFixture<StudentModuleResultDetailComponent>;
        const route = ({ data: of({ studentModuleResult: new StudentModuleResult(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentModuleResultDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentModuleResultDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleResultDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentModuleResult).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
