/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { MinisterialWorkExperienceUpdateComponent } from 'app/entities/ministerial-work-experience/ministerial-work-experience-update.component';
import { MinisterialWorkExperienceService } from 'app/entities/ministerial-work-experience/ministerial-work-experience.service';
import { MinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';

describe('Component Tests', () => {
    describe('MinisterialWorkExperience Management Update Component', () => {
        let comp: MinisterialWorkExperienceUpdateComponent;
        let fixture: ComponentFixture<MinisterialWorkExperienceUpdateComponent>;
        let service: MinisterialWorkExperienceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [MinisterialWorkExperienceUpdateComponent]
            })
                .overrideTemplate(MinisterialWorkExperienceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MinisterialWorkExperienceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MinisterialWorkExperienceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MinisterialWorkExperience(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ministerialWorkExperience = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MinisterialWorkExperience();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ministerialWorkExperience = entity;
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
