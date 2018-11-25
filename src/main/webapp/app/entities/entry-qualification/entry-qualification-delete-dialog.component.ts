import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntryQualification } from 'app/shared/model/entry-qualification.model';
import { EntryQualificationService } from './entry-qualification.service';

@Component({
    selector: 'jhi-entry-qualification-delete-dialog',
    templateUrl: './entry-qualification-delete-dialog.component.html'
})
export class EntryQualificationDeleteDialogComponent {
    entryQualification: IEntryQualification;

    constructor(
        private entryQualificationService: EntryQualificationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entryQualificationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'entryQualificationListModification',
                content: 'Deleted an entryQualification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entry-qualification-delete-popup',
    template: ''
})
export class EntryQualificationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entryQualification }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EntryQualificationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.entryQualification = entryQualification;
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
