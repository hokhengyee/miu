import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommonResources } from 'app/shared/model/common-resources.model';
import { CommonResourcesService } from './common-resources.service';

@Component({
    selector: 'jhi-common-resources-delete-dialog',
    templateUrl: './common-resources-delete-dialog.component.html'
})
export class CommonResourcesDeleteDialogComponent {
    commonResources: ICommonResources;

    constructor(
        private commonResourcesService: CommonResourcesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commonResourcesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commonResourcesListModification',
                content: 'Deleted an commonResources'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-common-resources-delete-popup',
    template: ''
})
export class CommonResourcesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commonResources }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommonResourcesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commonResources = commonResources;
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
