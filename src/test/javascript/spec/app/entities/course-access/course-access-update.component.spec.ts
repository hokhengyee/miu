/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseAccessUpdateComponent } from 'app/entities/course-access/course-access-update.component';
import { CourseAccessService } from 'app/entities/course-access/course-access.service';
import { CourseAccess } from 'app/shared/model/course-access.model';

describe('Component Tests', () => {
    describe('CourseAccess Management Update Component', () => {
        let comp: CourseAccessUpdateComponent;
        let fixture: ComponentFixture<CourseAccessUpdateComponent>;
        let service: CourseAccessService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseAccessUpdateComponent]
            })
                .overrideTemplate(CourseAccessUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourseAccessUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseAccessService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CourseAccess(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courseAccess = entity;
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
                    const entity = new CourseAccess();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courseAccess = entity;
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
