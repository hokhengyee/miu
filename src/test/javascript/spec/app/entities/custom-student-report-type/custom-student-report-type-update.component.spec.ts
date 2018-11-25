/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CustomStudentReportTypeUpdateComponent } from 'app/entities/custom-student-report-type/custom-student-report-type-update.component';
import { CustomStudentReportTypeService } from 'app/entities/custom-student-report-type/custom-student-report-type.service';
import { CustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';

describe('Component Tests', () => {
    describe('CustomStudentReportType Management Update Component', () => {
        let comp: CustomStudentReportTypeUpdateComponent;
        let fixture: ComponentFixture<CustomStudentReportTypeUpdateComponent>;
        let service: CustomStudentReportTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CustomStudentReportTypeUpdateComponent]
            })
                .overrideTemplate(CustomStudentReportTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CustomStudentReportTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomStudentReportTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CustomStudentReportType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.customStudentReportType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CustomStudentReportType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.customStudentReportType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
