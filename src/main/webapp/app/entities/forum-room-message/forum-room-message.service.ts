import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IForumRoomMessage } from 'app/shared/model/forum-room-message.model';

type EntityResponseType = HttpResponse<IForumRoomMessage>;
type EntityArrayResponseType = HttpResponse<IForumRoomMessage[]>;

@Injectable({ providedIn: 'root' })
export class ForumRoomMessageService {
    public resourceUrl = SERVER_API_URL + 'api/forum-room-messages';

    constructor(private http: HttpClient) {}

    create(forumRoomMessage: IForumRoomMessage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(forumRoomMessage);
        return this.http
            .post<IForumRoomMessage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(forumRoomMessage: IForumRoomMessage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(forumRoomMessage);
        return this.http
            .put<IForumRoomMessage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IForumRoomMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IForumRoomMessage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(forumRoomMessage: IForumRoomMessage): IForumRoomMessage {
        const copy: IForumRoomMessage = Object.assign({}, forumRoomMessage, {
            messageDatetime:
                forumRoomMessage.messageDatetime != null && forumRoomMessage.messageDatetime.isValid()
                    ? forumRoomMessage.messageDatetime.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.messageDatetime = res.body.messageDatetime != null ? moment(res.body.messageDatetime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((forumRoomMessage: IForumRoomMessage) => {
                forumRoomMessage.messageDatetime =
                    forumRoomMessage.messageDatetime != null ? moment(forumRoomMessage.messageDatetime) : null;
            });
        }
        return res;
    }
}
