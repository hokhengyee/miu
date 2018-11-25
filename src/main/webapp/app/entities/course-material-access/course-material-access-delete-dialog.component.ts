import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseMaterialAccess } from 'app/shared/model/course-material-access.model';
import { CourseMaterialAccessService } from './course-material-access.service';

@Component({
    selector: 'jhi-course-material-access-delete-dialog',
    templateUrl: './course-material-access-delete-dialog.component.html'
})
export class CourseMaterialAccessDeleteDialogComponent {
    courseMaterialAccess: ICourseMaterialAccess;

    constructor(
        private courseMaterialAccessService: CourseMaterialAccessService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courseMaterialAccessService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courseMaterialAccessListModification',
                content: 'Deleted an courseMaterialAccess'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-course-material-access-delete-popup',
    template: ''
})
export class CourseMaterialAccessDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courseMaterialAccess }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourseMaterialAccessDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.courseMaterialAccess = courseMaterialAccess;
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
