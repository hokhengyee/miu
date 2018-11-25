import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IForumRoom } from 'app/shared/model/forum-room.model';
import { ForumRoomService } from './forum-room.service';

@Component({
    selector: 'jhi-forum-room-update',
    templateUrl: './forum-room-update.component.html'
})
export class ForumRoomUpdateComponent implements OnInit {
    forumRoom: IForumRoom;
    isSaving: boolean;

    constructor(private forumRoomService: ForumRoomService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ forumRoom }) => {
            this.forumRoom = forumRoom;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.forumRoom.id !== undefined) {
            this.subscribeToSaveResponse(this.forumRoomService.update(this.forumRoom));
        } else {
            this.subscribeToSaveResponse(this.forumRoomService.create(this.forumRoom));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IForumRoom>>) {
        result.subscribe((res: HttpResponse<IForumRoom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
