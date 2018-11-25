import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecordOfCertificate } from 'app/shared/model/record-of-certificate.model';
import { RecordOfCertificateService } from './record-of-certificate.service';

@Component({
    selector: 'jhi-record-of-certificate-delete-dialog',
    templateUrl: './record-of-certificate-delete-dialog.component.html'
})
export class RecordOfCertificateDeleteDialogComponent {
    recordOfCertificate: IRecordOfCertificate;

    constructor(
        private recordOfCertificateService: RecordOfCertificateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recordOfCertificateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recordOfCertificateListModification',
                content: 'Deleted an recordOfCertificate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-record-of-certificate-delete-popup',
    template: ''
})
export class RecordOfCertificateDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recordOfCertificate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecordOfCertificateDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recordOfCertificate = recordOfCertificate;
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
