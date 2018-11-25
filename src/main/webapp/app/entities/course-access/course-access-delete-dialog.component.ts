import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseAccess } from 'app/shared/model/course-access.model';
import { CourseAccessService } from './course-access.service';

@Component({
    selector: 'jhi-course-access-delete-dialog',
    templateUrl: './course-access-delete-dialog.component.html'
})
export class CourseAccessDeleteDialogComponent {
    courseAccess: ICourseAccess;

    constructor(
        private courseAccessService: CourseAccessService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courseAccessService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courseAccessListModification',
                content: 'Deleted an courseAccess'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-course-access-delete-popup',
    template: ''
})
export class CourseAccessDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courseAccess }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourseAccessDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.courseAccess = courseAccess;
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
