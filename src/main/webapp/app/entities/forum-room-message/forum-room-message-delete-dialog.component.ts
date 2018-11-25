import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IForumRoomMessage } from 'app/shared/model/forum-room-message.model';
import { ForumRoomMessageService } from './forum-room-message.service';

@Component({
    selector: 'jhi-forum-room-message-delete-dialog',
    templateUrl: './forum-room-message-delete-dialog.component.html'
})
export class ForumRoomMessageDeleteDialogComponent {
    forumRoomMessage: IForumRoomMessage;

    constructor(
        private forumRoomMessageService: ForumRoomMessageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.forumRoomMessageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'forumRoomMessageListModification',
                content: 'Deleted an forumRoomMessage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-forum-room-message-delete-popup',
    template: ''
})
export class ForumRoomMessageDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ forumRoomMessage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ForumRoomMessageDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.forumRoomMessage = forumRoomMessage;
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
