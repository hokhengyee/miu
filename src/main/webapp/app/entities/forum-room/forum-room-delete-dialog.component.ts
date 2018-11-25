import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IForumRoom } from 'app/shared/model/forum-room.model';
import { ForumRoomService } from './forum-room.service';

@Component({
    selector: 'jhi-forum-room-delete-dialog',
    templateUrl: './forum-room-delete-dialog.component.html'
})
export class ForumRoomDeleteDialogComponent {
    forumRoom: IForumRoom;

    constructor(private forumRoomService: ForumRoomService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.forumRoomService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'forumRoomListModification',
                content: 'Deleted an forumRoom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-forum-room-delete-popup',
    template: ''
})
export class ForumRoomDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ forumRoom }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ForumRoomDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.forumRoom = forumRoom;
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
