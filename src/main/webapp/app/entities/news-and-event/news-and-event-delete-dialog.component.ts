import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INewsAndEvent } from 'app/shared/model/news-and-event.model';
import { NewsAndEventService } from './news-and-event.service';

@Component({
    selector: 'jhi-news-and-event-delete-dialog',
    templateUrl: './news-and-event-delete-dialog.component.html'
})
export class NewsAndEventDeleteDialogComponent {
    newsAndEvent: INewsAndEvent;

    constructor(
        private newsAndEventService: NewsAndEventService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.newsAndEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'newsAndEventListModification',
                content: 'Deleted an newsAndEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-news-and-event-delete-popup',
    template: ''
})
export class NewsAndEventDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ newsAndEvent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NewsAndEventDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.newsAndEvent = newsAndEvent;
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
