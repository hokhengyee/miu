import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResearchPaper } from 'app/shared/model/research-paper.model';
import { ResearchPaperService } from './research-paper.service';

@Component({
    selector: 'jhi-research-paper-delete-dialog',
    templateUrl: './research-paper-delete-dialog.component.html'
})
export class ResearchPaperDeleteDialogComponent {
    researchPaper: IResearchPaper;

    constructor(
        private researchPaperService: ResearchPaperService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.researchPaperService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'researchPaperListModification',
                content: 'Deleted an researchPaper'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-research-paper-delete-popup',
    template: ''
})
export class ResearchPaperDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ researchPaper }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResearchPaperDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.researchPaper = researchPaper;
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
