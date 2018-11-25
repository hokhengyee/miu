import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentPayment } from 'app/shared/model/student-payment.model';
import { StudentPaymentService } from './student-payment.service';

@Component({
    selector: 'jhi-student-payment-delete-dialog',
    templateUrl: './student-payment-delete-dialog.component.html'
})
export class StudentPaymentDeleteDialogComponent {
    studentPayment: IStudentPayment;

    constructor(
        private studentPaymentService: StudentPaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentPaymentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentPaymentListModification',
                content: 'Deleted an studentPayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-payment-delete-popup',
    template: ''
})
export class StudentPaymentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentPayment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentPaymentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentPayment = studentPayment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
