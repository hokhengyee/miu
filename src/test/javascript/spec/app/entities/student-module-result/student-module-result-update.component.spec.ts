/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentModuleResultUpdateComponent } from 'app/entities/student-module-result/student-module-result-update.component';
import { StudentModuleResultService } from 'app/entities/student-module-result/student-module-result.service';
import { StudentModuleResult } from 'app/shared/model/student-module-result.model';

describe('Component Tests', () => {
    describe('StudentModuleResult Management Update Component', () => {
        let comp: StudentModuleResultUpdateComponent;
        let fixture: ComponentFixture<StudentModuleResultUpdateComponent>;
        let service: StudentModuleResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentModuleResultUpdateComponent]
            })
                .overrideTemplate(StudentModuleResultUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentModuleResultUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleResultService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModuleResult(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModuleResult = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModuleResult();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModuleResult = entity;
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
