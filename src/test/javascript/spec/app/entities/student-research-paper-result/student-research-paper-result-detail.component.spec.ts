/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentResearchPaperResultDetailComponent } from 'app/entities/student-research-paper-result/student-research-paper-result-detail.component';
import { StudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';

describe('Component Tests', () => {
    describe('StudentResearchPaperResult Management Detail Component', () => {
        let comp: StudentResearchPaperResultDetailComponent;
        let fixture: ComponentFixture<StudentResearchPaperResultDetailComponent>;
        const route = ({ data: of({ studentResearchPaperResult: new StudentResearchPaperResult(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentResearchPaperResultDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentResearchPaperResultDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentResearchPaperResultDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentResearchPaperResult).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
