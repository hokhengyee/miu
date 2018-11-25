import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';
import { MinisterialWorkExperienceService } from './ministerial-work-experience.service';

@Component({
    selector: 'jhi-ministerial-work-experience-delete-dialog',
    templateUrl: './ministerial-work-experience-delete-dialog.component.html'
})
export class MinisterialWorkExperienceDeleteDialogComponent {
    ministerialWorkExperience: IMinisterialWorkExperience;

    constructor(
        private ministerialWorkExperienceService: MinisterialWorkExperienceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ministerialWorkExperienceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ministerialWorkExperienceListModification',
                content: 'Deleted an ministerialWorkExperience'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ministerial-work-experience-delete-popup',
    template: ''
})
export class MinisterialWorkExperienceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ministerialWorkExperience }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MinisterialWorkExperienceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ministerialWorkExperience = ministerialWorkExperience;
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
