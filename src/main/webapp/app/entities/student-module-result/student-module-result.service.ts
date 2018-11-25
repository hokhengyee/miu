import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentModuleResult } from 'app/shared/model/student-module-result.model';

type EntityResponseType = HttpResponse<IStudentModuleResult>;
type EntityArrayResponseType = HttpResponse<IStudentModuleResult[]>;

@Injectable({ providedIn: 'root' })
export class StudentModuleResultService {
    public resourceUrl = SERVER_API_URL + 'api/student-module-results';

    constructor(private http: HttpClient) {}

    create(studentModuleResult: IStudentModuleResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentModuleResult);
        return this.http
            .post<IStudentModuleResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(studentModuleResult: IStudentModuleResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentModuleResult);
        return this.http
            .put<IStudentModuleResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStudentModuleResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentModuleResult[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(studentModuleResult: IStudentModuleResult): IStudentModuleResult {
        const copy: IStudentModuleResult = Object.assign({}, studentModuleResult, {
            dateGraded:
                studentModuleResult.dateGraded != null && studentModuleResult.dateGraded.isValid()
                    ? studentModuleResult.dateGraded.format(DATE_FORMAT)
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
            res.body.forEach((studentModuleResult: IStudentModuleResult) => {
                studentModuleResult.dateGraded = studentModuleResult.dateGraded != null ? moment(studentModuleResult.dateGraded) : null;
            });
        }
        return res;
    }
}
