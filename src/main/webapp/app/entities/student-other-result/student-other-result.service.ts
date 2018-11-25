import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentOtherResult } from 'app/shared/model/student-other-result.model';

type EntityResponseType = HttpResponse<IStudentOtherResult>;
type EntityArrayResponseType = HttpResponse<IStudentOtherResult[]>;

@Injectable({ providedIn: 'root' })
export class StudentOtherResultService {
    public resourceUrl = SERVER_API_URL + 'api/student-other-results';

    constructor(private http: HttpClient) {}

    create(studentOtherResult: IStudentOtherResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentOtherResult);
        return this.http
            .post<IStudentOtherResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(studentOtherResult: IStudentOtherResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentOtherResult);
        return this.http
            .put<IStudentOtherResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStudentOtherResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentOtherResult[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(studentOtherResult: IStudentOtherResult): IStudentOtherResult {
        const copy: IStudentOtherResult = Object.assign({}, studentOtherResult, {
            dateGraded:
                studentOtherResult.dateGraded != null && studentOtherResult.dateGraded.isValid()
                    ? studentOtherResult.dateGraded.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateGraded = res.body.dateGraded != null ? moment(res.body.dateGraded) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((studentOtherResult: IStudentOtherResult) => {
                studentOtherResult.dateGraded = studentOtherResult.dateGraded != null ? moment(studentOtherResult.dateGraded) : null;
            });
        }
        return res;
    }
}
