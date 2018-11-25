/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentResearchPaperResultUpdateComponent } from 'app/entities/student-research-paper-result/student-research-paper-result-update.component';
import { StudentResearchPaperResultService } from 'app/entities/student-research-paper-result/student-research-paper-result.service';
import { StudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';

describe('Component Tests', () => {
    describe('StudentResearchPaperResult Management Update Component', () => {
        let comp: StudentResearchPaperResultUpdateComponent;
        let fixture: ComponentFixture<StudentResearchPaperResultUpdateComponent>;
        let service: StudentResearchPaperResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentResearchPaperResultUpdateComponent]
            })
                .overrideTemplate(StudentResearchPaperResultUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentResearchPaperResultUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentResearchPaperResultService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentResearchPaperResult(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentResearchPaperResult = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentResearchPaperResult();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentResearchPaperResult = entity;
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
