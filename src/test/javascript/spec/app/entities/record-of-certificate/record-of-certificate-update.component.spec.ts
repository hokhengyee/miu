/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { RecordOfCertificateUpdateComponent } from 'app/entities/record-of-certificate/record-of-certificate-update.component';
import { RecordOfCertificateService } from 'app/entities/record-of-certificate/record-of-certificate.service';
import { RecordOfCertificate } from 'app/shared/model/record-of-certificate.model';

describe('Component Tests', () => {
    describe('RecordOfCertificate Management Update Component', () => {
        let comp: RecordOfCertificateUpdateComponent;
        let fixture: ComponentFixture<RecordOfCertificateUpdateComponent>;
        let service: RecordOfCertificateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [RecordOfCertificateUpdateComponent]
            })
                .overrideTemplate(RecordOfCertificateUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecordOfCertificateUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordOfCertificateService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RecordOfCertificate(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.recordOfCertificate = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RecordOfCertificate();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.recordOfCertificate = entity;
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
