import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStaticPage } from 'app/shared/model/static-page.model';
import { StaticPageService } from './static-page.service';

@Component({
    selector: 'jhi-static-page-delete-dialog',
    templateUrl: './static-page-delete-dialog.component.html'
})
export class StaticPageDeleteDialogComponent {
    staticPage: IStaticPage;

    constructor(private staticPageService: StaticPageService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.staticPageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'staticPageListModification',
                content: 'Deleted an staticPage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-static-page-delete-popup',
    template: ''
})
export class StaticPageDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ staticPage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StaticPageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.staticPage = staticPage;
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
