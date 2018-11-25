import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IForumRoomMessage } from 'app/shared/model/forum-room-message.model';
import { ForumRoomMessageService } from './forum-room-message.service';
import { IForumRoom } from 'app/shared/model/forum-room.model';
import { ForumRoomService } from 'app/entities/forum-room';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-forum-room-message-update',
    templateUrl: './forum-room-message-update.component.html'
})
export class ForumRoomMessageUpdateComponent implements OnInit {
    forumRoomMessage: IForumRoomMessage;
    isSaving: boolean;

    forumrooms: IForumRoom[];

    users: IUser[];
    messageDatetime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private forumRoomMessageService: ForumRoomMessageService,
        private forumRoomService: ForumRoomService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ forumRoomMessage }) => {
            this.forumRoomMessage = forumRoomMessage;
            this.messageDatetime =
                this.forumRoomMessage.messageDatetime != null ? this.forumRoomMessage.messageDatetime.format(DATE_TIME_FORMAT) : null;
        });
        this.forumRoomService.query().subscribe(
            (res: HttpResponse<IForumRoom[]>) => {
                this.forumrooms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.forumRoomMessage.messageDatetime = this.messageDatetime != null ? moment(this.messageDatetime, DATE_TIME_FORMAT) : null;
        if (this.forumRoomMessage.id !== undefined) {
            this.subscribeToSaveResponse(this.forumRoomMessageService.update(this.forumRoomMessage));
        } else {
            this.subscribeToSaveResponse(this.forumRoomMessageService.create(this.forumRoomMessage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IForumRoomMessage>>) {
        result.subscribe((res: HttpResponse<IForumRoomMessage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackForumRoomById(index: number, item: IForumRoom) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
