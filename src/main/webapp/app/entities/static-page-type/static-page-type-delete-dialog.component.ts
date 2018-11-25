import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStaticPageType } from 'app/shared/model/static-page-type.model';
import { StaticPageTypeService } from './static-page-type.service';

@Component({
    selector: 'jhi-static-page-type-delete-dialog',
    templateUrl: './static-page-type-delete-dialog.component.html'
})
export class StaticPageTypeDeleteDialogComponent {
    staticPageType: IStaticPageType;

    constructor(
        private staticPageTypeService: StaticPageTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.staticPageTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'staticPageTypeListModification',
                content: 'Deleted an staticPageType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-static-page-type-delete-popup',
    template: ''
})
export class StaticPageTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ staticPageType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StaticPageTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.staticPageType = staticPageType;
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
