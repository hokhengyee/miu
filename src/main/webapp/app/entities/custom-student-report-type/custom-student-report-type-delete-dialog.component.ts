import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';
import { CustomStudentReportTypeService } from './custom-student-report-type.service';

@Component({
    selector: 'jhi-custom-student-report-type-delete-dialog',
    templateUrl: './custom-student-report-type-delete-dialog.component.html'
})
export class CustomStudentReportTypeDeleteDialogComponent {
    customStudentReportType: ICustomStudentReportType;

    constructor(
        private customStudentReportTypeService: CustomStudentReportTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customStudentReportTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'customStudentReportTypeListModification',
                content: 'Deleted an customStudentReportType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-custom-student-report-type-delete-popup',
    template: ''
})
export class CustomStudentReportTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ customStudentReportType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CustomStudentReportTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.customStudentReportType = customStudentReportType;
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
