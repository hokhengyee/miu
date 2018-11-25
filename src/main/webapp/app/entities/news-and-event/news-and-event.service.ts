import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INewsAndEvent } from 'app/shared/model/news-and-event.model';

type EntityResponseType = HttpResponse<INewsAndEvent>;
type EntityArrayResponseType = HttpResponse<INewsAndEvent[]>;

@Injectable({ providedIn: 'root' })
export class NewsAndEventService {
    public resourceUrl = SERVER_API_URL + 'api/news-and-events';

    constructor(private http: HttpClient) {}

    create(newsAndEvent: INewsAndEvent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(newsAndEvent);
        return this.http
            .post<INewsAndEvent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(newsAndEvent: INewsAndEvent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(newsAndEvent);
        return this.http
            .put<INewsAndEvent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<INewsAndEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<INewsAndEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(newsAndEvent: INewsAndEvent): INewsAndEvent {
        const copy: INewsAndEvent = Object.assign({}, newsAndEvent, {
            startDT: newsAndEvent.startDT != null && newsAndEvent.startDT.isValid() ? newsAndEvent.startDT.toJSON() : null,
            endDT: newsAndEvent.endDT != null && newsAndEvent.endDT.isValid() ? newsAndEvent.endDT.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDT = res.body.startDT != null ? moment(res.body.startDT) : null;
            res.body.endDT = res.body.endDT != null ? moment(res.body.endDT) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((newsAndEvent: INewsAndEvent) => {
                newsAndEvent.startDT = newsAndEvent.startDT != null ? moment(newsAndEvent.startDT) : null;
                newsAndEvent.endDT = newsAndEvent.endDT != null ? moment(newsAndEvent.endDT) : null;
            });
        }
        return res;
    }
}
