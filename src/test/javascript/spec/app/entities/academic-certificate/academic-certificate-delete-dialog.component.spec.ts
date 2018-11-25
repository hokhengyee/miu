/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { AcademicCertificateDeleteDialogComponent } from 'app/entities/academic-certificate/academic-certificate-delete-dialog.component';
import { AcademicCertificateService } from 'app/entities/academic-certificate/academic-certificate.service';

describe('Component Tests', () => {
    describe('AcademicCertificate Management Delete Component', () => {
        let comp: AcademicCertificateDeleteDialogComponent;
        let fixture: ComponentFixture<AcademicCertificateDeleteDialogComponent>;
        let service: AcademicCertificateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [AcademicCertificateDeleteDialogComponent]
            })
                .overrideTemplate(AcademicCertificateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcademicCertificateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademicCertificateService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
