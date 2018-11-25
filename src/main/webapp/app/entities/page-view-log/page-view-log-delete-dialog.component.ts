import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPageViewLog } from 'app/shared/model/page-view-log.model';
import { PageViewLogService } from './page-view-log.service';

@Component({
    selector: 'jhi-page-view-log-delete-dialog',
    templateUrl: './page-view-log-delete-dialog.component.html'
})
export class PageViewLogDeleteDialogComponent {
    pageViewLog: IPageViewLog;

    constructor(
        private pageViewLogService: PageViewLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pageViewLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pageViewLogListModification',
                content: 'Deleted an pageViewLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-page-view-log-delete-popup',
    template: ''
})
export class PageViewLogDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pageViewLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PageViewLogDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pageViewLog = pageViewLog;
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
