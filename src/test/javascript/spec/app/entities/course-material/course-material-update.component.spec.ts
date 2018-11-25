/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseMaterialUpdateComponent } from 'app/entities/course-material/course-material-update.component';
import { CourseMaterialService } from 'app/entities/course-material/course-material.service';
import { CourseMaterial } from 'app/shared/model/course-material.model';

describe('Component Tests', () => {
    describe('CourseMaterial Management Update Component', () => {
        let comp: CourseMaterialUpdateComponent;
        let fixture: ComponentFixture<CourseMaterialUpdateComponent>;
        let service: CourseMaterialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseMaterialUpdateComponent]
            })
                .overrideTemplate(CourseMaterialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourseMaterialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseMaterialService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CourseMaterial(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.courseMaterial = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CourseMaterial();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.courseMaterial = entity;
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
