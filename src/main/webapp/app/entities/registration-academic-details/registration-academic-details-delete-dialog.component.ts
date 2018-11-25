import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';
import { RegistrationAcademicDetailsService } from './registration-academic-details.service';

@Component({
    selector: 'jhi-registration-academic-details-delete-dialog',
    templateUrl: './registration-academic-details-delete-dialog.component.html'
})
export class RegistrationAcademicDetailsDeleteDialogComponent {
    registrationAcademicDetails: IRegistrationAcademicDetails;

    constructor(
        private registrationAcademicDetailsService: RegistrationAcademicDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.registrationAcademicDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'registrationAcademicDetailsListModification',
                content: 'Deleted an registrationAcademicDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-registration-academic-details-delete-popup',
    template: ''
})
export class RegistrationAcademicDetailsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ registrationAcademicDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RegistrationAcademicDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.registrationAcademicDetails = registrationAcademicDetails;
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
