import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';

type EntityResponseType = HttpResponse<IStudentResearchPaperResult>;
type EntityArrayResponseType = HttpResponse<IStudentResearchPaperResult[]>;

@Injectable({ providedIn: 'root' })
export class StudentResearchPaperResultService {
    public resourceUrl = SERVER_API_URL + 'api/student-research-paper-results';

    constructor(private http: HttpClient) {}

    create(studentResearchPaperResult: IStudentResearchPaperResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentResearchPaperResult);
        return this.http
            .post<IStudentResearchPaperResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(studentResearchPaperResult: IStudentResearchPaperResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentResearchPaperResult);
        return this.http
            .put<IStudentResearchPaperResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStudentResearchPaperResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentResearchPaperResult[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(studentResearchPaperResult: IStudentResearchPaperResult): IStudentResearchPaperResult {
        const copy: IStudentResearchPaperResult = Object.assign({}, studentResearchPaperResult, {
            dateGraded:
                studentResearchPaperResult.dateGraded != null && studentResearchPaperResult.dateGraded.isValid()
                    ? studentResearchPaperResult.dateGraded.format(DATE_FORMAT)
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
            res.body.forEach((studentResearchPaperResult: IStudentResearchPaperResult) => {
                studentResearchPaperResult.dateGraded =
                    studentResearchPaperResult.dateGraded != null ? moment(studentResearchPaperResult.dateGraded) : null;
            });
        }
        return res;
    }
}
