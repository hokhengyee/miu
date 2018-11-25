import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecordOfCertificate } from 'app/shared/model/record-of-certificate.model';

type EntityResponseType = HttpResponse<IRecordOfCertificate>;
type EntityArrayResponseType = HttpResponse<IRecordOfCertificate[]>;

@Injectable({ providedIn: 'root' })
export class RecordOfCertificateService {
    public resourceUrl = SERVER_API_URL + 'api/record-of-certificates';

    constructor(private http: HttpClient) {}

    create(recordOfCertificate: IRecordOfCertificate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recordOfCertificate);
        return this.http
            .post<IRecordOfCertificate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(recordOfCertificate: IRecordOfCertificate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recordOfCertificate);
        return this.http
            .put<IRecordOfCertificate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRecordOfCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRecordOfCertificate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(recordOfCertificate: IRecordOfCertificate): IRecordOfCertificate {
        const copy: IRecordOfCertificate = Object.assign({}, recordOfCertificate, {
            certDate:
                recordOfCertificate.certDate != null && recordOfCertificate.certDate.isValid()
                    ? recordOfCertificate.certDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.certDate = res.body.certDate != null ? moment(res.body.certDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((recordOfCertificate: IRecordOfCertificate) => {
                recordOfCertificate.certDate = recordOfCertificate.certDate != null ? moment(recordOfCertificate.certDate) : null;
            });
        }
        return res;
    }
}
