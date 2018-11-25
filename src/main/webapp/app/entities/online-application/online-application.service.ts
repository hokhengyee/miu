import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOnlineApplication } from 'app/shared/model/online-application.model';

type EntityResponseType = HttpResponse<IOnlineApplication>;
type EntityArrayResponseType = HttpResponse<IOnlineApplication[]>;

@Injectable({ providedIn: 'root' })
export class OnlineApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/online-applications';

    constructor(private http: HttpClient) {}

    create(onlineApplication: IOnlineApplication): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(onlineApplication);
        return this.http
            .post<IOnlineApplication>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(onlineApplication: IOnlineApplication): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(onlineApplication);
        return this.http
            .put<IOnlineApplication>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOnlineApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOnlineApplication[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(onlineApplication: IOnlineApplication): IOnlineApplication {
        const copy: IOnlineApplication = Object.assign({}, onlineApplication, {
            dateOfBirth:
                onlineApplication.dateOfBirth != null && onlineApplication.dateOfBirth.isValid()
                    ? onlineApplication.dateOfBirth.format(DATE_FORMAT)
                    : null,
            registrationDatetime:
                onlineApplication.registrationDatetime != null && onlineApplication.registrationDatetime.isValid()
                    ? onlineApplication.registrationDatetime.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
            res.body.registrationDatetime = res.body.registrationDatetime != null ? moment(res.body.registrationDatetime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((onlineApplication: IOnlineApplication) => {
                onlineApplication.dateOfBirth = onlineApplication.dateOfBirth != null ? moment(onlineApplication.dateOfBirth) : null;
                onlineApplication.registrationDatetime =
                    onlineApplication.registrationDatetime != null ? moment(onlineApplication.registrationDatetime) : null;
            });
        }
        return res;
    }
}
