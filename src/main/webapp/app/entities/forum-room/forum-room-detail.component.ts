import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IForumRoom } from 'app/shared/model/forum-room.model';

@Component({
    selector: 'jhi-forum-room-detail',
    templateUrl: './forum-room-detail.component.html'
})
export class ForumRoomDetailComponent implements OnInit {
    forumRoom: IForumRoom;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ forumRoom }) => {
            this.forumRoom = forumRoom;
        });
    }

    previousState() {
        window.history.back();
    }
}
