/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentOtherResultUpdateComponent } from 'app/entities/student-other-result/student-other-result-update.component';
import { StudentOtherResultService } from 'app/entities/student-other-result/student-other-result.service';
import { StudentOtherResult } from 'app/shared/model/student-other-result.model';

describe('Component Tests', () => {
    describe('StudentOtherResult Management Update Component', () => {
        let comp: StudentOtherResultUpdateComponent;
        let fixture: ComponentFixture<StudentOtherResultUpdateComponent>;
        let service: StudentOtherResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentOtherResultUpdateComponent]
            })
                .overrideTemplate(StudentOtherResultUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentOtherResultUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentOtherResultService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentOtherResult(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentOtherResult = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentOtherResult();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentOtherResult = entity;
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
