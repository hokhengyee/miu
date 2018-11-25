/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { RecordOfCertificateDeleteDialogComponent } from 'app/entities/record-of-certificate/record-of-certificate-delete-dialog.component';
import { RecordOfCertificateService } from 'app/entities/record-of-certificate/record-of-certificate.service';

describe('Component Tests', () => {
    describe('RecordOfCertificate Management Delete Component', () => {
        let comp: RecordOfCertificateDeleteDialogComponent;
        let fixture: ComponentFixture<RecordOfCertificateDeleteDialogComponent>;
        let service: RecordOfCertificateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [RecordOfCertificateDeleteDialogComponent]
            })
                .overrideTemplate(RecordOfCertificateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecordOfCertificateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordOfCertificateService);
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
