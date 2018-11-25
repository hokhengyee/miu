import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';
import { LecturerProfileService } from './lecturer-profile.service';

@Component({
    selector: 'jhi-lecturer-profile-delete-dialog',
    templateUrl: './lecturer-profile-delete-dialog.component.html'
})
export class LecturerProfileDeleteDialogComponent {
    lecturerProfile: ILecturerProfile;

    constructor(
        private lecturerProfileService: LecturerProfileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lecturerProfileService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lecturerProfileListModification',
                content: 'Deleted an lecturerProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lecturer-profile-delete-popup',
    template: ''
})
export class LecturerProfileDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lecturerProfile }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LecturerProfileDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.lecturerProfile = lecturerProfile;
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
