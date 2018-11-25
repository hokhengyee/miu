import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IForumRoom } from 'app/shared/model/forum-room.model';

type EntityResponseType = HttpResponse<IForumRoom>;
type EntityArrayResponseType = HttpResponse<IForumRoom[]>;

@Injectable({ providedIn: 'root' })
export class ForumRoomService {
    public resourceUrl = SERVER_API_URL + 'api/forum-rooms';

    constructor(private http: HttpClient) {}

    create(forumRoom: IForumRoom): Observable<EntityResponseType> {
        return this.http.post<IForumRoom>(this.resourceUrl, forumRoom, { observe: 'response' });
    }

    update(forumRoom: IForumRoom): Observable<EntityResponseType> {
        return this.http.put<IForumRoom>(this.resourceUrl, forumRoom, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IForumRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IForumRoom[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
