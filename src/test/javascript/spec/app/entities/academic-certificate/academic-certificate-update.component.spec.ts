/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { AcademicCertificateUpdateComponent } from 'app/entities/academic-certificate/academic-certificate-update.component';
import { AcademicCertificateService } from 'app/entities/academic-certificate/academic-certificate.service';
import { AcademicCertificate } from 'app/shared/model/academic-certificate.model';

describe('Component Tests', () => {
    describe('AcademicCertificate Management Update Component', () => {
        let comp: AcademicCertificateUpdateComponent;
        let fixture: ComponentFixture<AcademicCertificateUpdateComponent>;
        let service: AcademicCertificateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [AcademicCertificateUpdateComponent]
            })
                .overrideTemplate(AcademicCertificateUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcademicCertificateUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademicCertificateService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AcademicCertificate(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.academicCertificate = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AcademicCertificate();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.academicCertificate = entity;
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
