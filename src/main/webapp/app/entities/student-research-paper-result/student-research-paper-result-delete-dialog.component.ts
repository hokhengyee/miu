import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';
import { StudentResearchPaperResultService } from './student-research-paper-result.service';

@Component({
    selector: 'jhi-student-research-paper-result-delete-dialog',
    templateUrl: './student-research-paper-result-delete-dialog.component.html'
})
export class StudentResearchPaperResultDeleteDialogComponent {
    studentResearchPaperResult: IStudentResearchPaperResult;

    constructor(
        private studentResearchPaperResultService: StudentResearchPaperResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentResearchPaperResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentResearchPaperResultListModification',
                content: 'Deleted an studentResearchPaperResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-research-paper-result-delete-popup',
    template: ''
})
export class StudentResearchPaperResultDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentResearchPaperResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentResearchPaperResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentResearchPaperResult = studentResearchPaperResult;
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
