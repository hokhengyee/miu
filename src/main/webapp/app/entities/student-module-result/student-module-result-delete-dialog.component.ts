import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentModuleResult } from 'app/shared/model/student-module-result.model';
import { StudentModuleResultService } from './student-module-result.service';

@Component({
    selector: 'jhi-student-module-result-delete-dialog',
    templateUrl: './student-module-result-delete-dialog.component.html'
})
export class StudentModuleResultDeleteDialogComponent {
    studentModuleResult: IStudentModuleResult;

    constructor(
        private studentModuleResultService: StudentModuleResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentModuleResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentModuleResultListModification',
                content: 'Deleted an studentModuleResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-module-result-delete-popup',
    template: ''
})
export class StudentModuleResultDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModuleResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentModuleResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentModuleResult = studentModuleResult;
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
