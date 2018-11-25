import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOnlineApplication } from 'app/shared/model/online-application.model';
import { OnlineApplicationService } from './online-application.service';

@Component({
    selector: 'jhi-online-application-delete-dialog',
    templateUrl: './online-application-delete-dialog.component.html'
})
export class OnlineApplicationDeleteDialogComponent {
    onlineApplication: IOnlineApplication;

    constructor(
        private onlineApplicationService: OnlineApplicationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.onlineApplicationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'onlineApplicationListModification',
                content: 'Deleted an onlineApplication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-online-application-delete-popup',
    template: ''
})
export class OnlineApplicationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ onlineApplication }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OnlineApplicationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.onlineApplication = onlineApplication;
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
