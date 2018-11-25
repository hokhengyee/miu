/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { StudentPaymentDeleteDialogComponent } from 'app/entities/student-payment/student-payment-delete-dialog.component';
import { StudentPaymentService } from 'app/entities/student-payment/student-payment.service';

describe('Component Tests', () => {
    describe('StudentPayment Management Delete Component', () => {
        let comp: StudentPaymentDeleteDialogComponent;
        let fixture: ComponentFixture<StudentPaymentDeleteDialogComponent>;
        let service: StudentPaymentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentPaymentDeleteDialogComponent]
            })
                .overrideTemplate(StudentPaymentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentPaymentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentPaymentService);
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
