/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseMaterialAccessUpdateComponent } from 'app/entities/course-material-access/course-material-access-update.component';
import { CourseMaterialAccessService } from 'app/entities/course-material-access/course-material-access.service';
import { CourseMaterialAccess } from 'app/shared/model/course-material-access.model';

describe('Component Tests', () => {
    describe('CourseMaterialAccess Management Update Component', () => {
        let comp: CourseMaterialAccessUpdateComponent;
        let fixture: ComponentFixture<CourseMaterialAccessUpdateComponent>;
        let service: CourseMaterialAccessService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseMaterialAccessUpdateComponent]
            })
                .overrideTemplate(CourseMaterialAccessUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourseMaterialAccessUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseMaterialAccessService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CourseMaterialAccess(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courseMaterialAccess = entity;
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
                    const entity = new CourseMaterialAccess();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courseMaterialAccess = entity;
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
