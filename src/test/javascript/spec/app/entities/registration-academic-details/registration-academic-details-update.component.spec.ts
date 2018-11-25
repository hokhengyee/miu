/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { RegistrationAcademicDetailsUpdateComponent } from 'app/entities/registration-academic-details/registration-academic-details-update.component';
import { RegistrationAcademicDetailsService } from 'app/entities/registration-academic-details/registration-academic-details.service';
import { RegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';

describe('Component Tests', () => {
    describe('RegistrationAcademicDetails Management Update Component', () => {
        let comp: RegistrationAcademicDetailsUpdateComponent;
        let fixture: ComponentFixture<RegistrationAcademicDetailsUpdateComponent>;
        let service: RegistrationAcademicDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [RegistrationAcademicDetailsUpdateComponent]
            })
                .overrideTemplate(RegistrationAcademicDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegistrationAcademicDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegistrationAcademicDetailsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RegistrationAcademicDetails(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.registrationAcademicDetails = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RegistrationAcademicDetails();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.registrationAcademicDetails = entity;
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
