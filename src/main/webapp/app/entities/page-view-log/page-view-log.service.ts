import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPageViewLog } from 'app/shared/model/page-view-log.model';

type EntityResponseType = HttpResponse<IPageViewLog>;
type EntityArrayResponseType = HttpResponse<IPageViewLog[]>;

@Injectable({ providedIn: 'root' })
export class PageViewLogService {
    public resourceUrl = SERVER_API_URL + 'api/page-view-logs';

    constructor(private http: HttpClient) {}

    create(pageViewLog: IPageViewLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pageViewLog);
        return this.http
            .post<IPageViewLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pageViewLog: IPageViewLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pageViewLog);
        return this.http
            .put<IPageViewLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPageViewLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPageViewLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(pageViewLog: IPageViewLog): IPageViewLog {
        const copy: IPageViewLog = Object.assign({}, pageViewLog, {
            createdDate:
                pageViewLog.createdDate != null && pageViewLog.createdDate.isValid() ? pageViewLog.createdDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((pageViewLog: IPageViewLog) => {
                pageViewLog.createdDate = pageViewLog.createdDate != null ? moment(pageViewLog.createdDate) : null;
            });
        }
        return res;
    }
}
