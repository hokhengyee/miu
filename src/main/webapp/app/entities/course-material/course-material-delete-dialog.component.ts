import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseMaterial } from 'app/shared/model/course-material.model';
import { CourseMaterialService } from './course-material.service';

@Component({
    selector: 'jhi-course-material-delete-dialog',
    templateUrl: './course-material-delete-dialog.component.html'
})
export class CourseMaterialDeleteDialogComponent {
    courseMaterial: ICourseMaterial;

    constructor(
        private courseMaterialService: CourseMaterialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courseMaterialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courseMaterialListModification',
                content: 'Deleted an courseMaterial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-course-material-delete-popup',
    template: ''
})
export class CourseMaterialDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courseMaterial }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourseMaterialDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.courseMaterial = courseMaterial;
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
