import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentProfile } from 'app/shared/model/student-profile.model';

type EntityResponseType = HttpResponse<IStudentProfile>;
type EntityArrayResponseType = HttpResponse<IStudentProfile[]>;

@Injectable({ providedIn: 'root' })
export class StudentProfileService {
    public resourceUrl = SERVER_API_URL + 'api/student-profiles';

    constructor(private http: HttpClient) {}

    create(studentProfile: IStudentProfile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentProfile);
        return this.http
            .post<IStudentProfile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(studentProfile: IStudentProfile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentProfile);
        return this.http
            .put<IStudentProfile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStudentProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(studentProfile: IStudentProfile): IStudentProfile {
        const copy: IStudentProfile = Object.assign({}, studentProfile, {
            dateOfBirth:
                studentProfile.dateOfBirth != null && studentProfile.dateOfBirth.isValid()
                    ? studentProfile.dateOfBirth.format(DATE_FORMAT)
                    : null,
            applicationDate:
                studentProfile.applicationDate != null && studentProfile.applicationDate.isValid()
                    ? studentProfile.applicationDate.format(DATE_FORMAT)
                    : null,
            commencementDate:
                studentProfile.commencementDate != null && studentProfile.commencementDate.isValid()
                    ? studentProfile.commencementDate.format(DATE_FORMAT)
                    : null,
            completionDate:
                studentProfile.completionDate != null && studentProfile.completionDate.isValid()
                    ? studentProfile.completionDate.format(DATE_FORMAT)
                    : null,
            extendedCompletionDate:
                studentProfile.extendedCompletionDate != null && studentProfile.extendedCompletionDate.isValid()
                    ? studentProfile.extendedCompletionDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
            res.body.applicationDate = res.body.applicationDate != null ? moment(res.body.applicationDate) : null;
            res.body.commencementDate = res.body.commencementDate != null ? moment(res.body.commencementDate) : null;
            res.body.completionDate = res.body.completionDate != null ? moment(res.body.completionDate) : null;
            res.body.extendedCompletionDate = res.body.extendedCompletionDate != null ? moment(res.body.extendedCompletionDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((studentProfile: IStudentProfile) => {
                studentProfile.dateOfBirth = studentProfile.dateOfBirth != null ? moment(studentProfile.dateOfBirth) : null;
                studentProfile.applicationDate = studentProfile.applicationDate != null ? moment(studentProfile.applicationDate) : null;
                studentProfile.commencementDate = studentProfile.commencementDate != null ? moment(studentProfile.commencementDate) : null;
                studentProfile.completionDate = studentProfile.completionDate != null ? moment(studentProfile.completionDate) : null;
                studentProfile.extendedCompletionDate =
                    studentProfile.extendedCompletionDate != null ? moment(studentProfile.extendedCompletionDate) : null;
            });
        }
        return res;
    }
}
