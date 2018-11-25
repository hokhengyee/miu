import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';
import { AdjunctFacultyService } from './adjunct-faculty.service';

@Component({
    selector: 'jhi-adjunct-faculty-delete-dialog',
    templateUrl: './adjunct-faculty-delete-dialog.component.html'
})
export class AdjunctFacultyDeleteDialogComponent {
    adjunctFaculty: IAdjunctFaculty;

    constructor(
        private adjunctFacultyService: AdjunctFacultyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adjunctFacultyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adjunctFacultyListModification',
                content: 'Deleted an adjunctFaculty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adjunct-faculty-delete-popup',
    template: ''
})
export class AdjunctFacultyDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adjunctFaculty }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdjunctFacultyDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adjunctFaculty = adjunctFaculty;
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
