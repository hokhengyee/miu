import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentOtherResult } from 'app/shared/model/student-other-result.model';
import { StudentOtherResultService } from './student-other-result.service';

@Component({
    selector: 'jhi-student-other-result-delete-dialog',
    templateUrl: './student-other-result-delete-dialog.component.html'
})
export class StudentOtherResultDeleteDialogComponent {
    studentOtherResult: IStudentOtherResult;

    constructor(
        private studentOtherResultService: StudentOtherResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentOtherResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentOtherResultListModification',
                content: 'Deleted an studentOtherResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-other-result-delete-popup',
    template: ''
})
export class StudentOtherResultDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentOtherResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentOtherResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentOtherResult = studentOtherResult;
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
