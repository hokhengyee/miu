import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IForumRoomMessage } from 'app/shared/model/forum-room-message.model';

@Component({
    selector: 'jhi-forum-room-message-detail',
    templateUrl: './forum-room-message-detail.component.html'
})
export class ForumRoomMessageDetailComponent implements OnInit {
    forumRoomMessage: IForumRoomMessage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ forumRoomMessage }) => {
            this.forumRoomMessage = forumRoomMessage;
        });
    }

    previousState() {
        window.history.back();
    }
}
