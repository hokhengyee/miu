import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';

type EntityResponseType = HttpResponse<ICustomStudentReportType>;
type EntityArrayResponseType = HttpResponse<ICustomStudentReportType[]>;

@Injectable({ providedIn: 'root' })
export class CustomStudentReportTypeService {
    public resourceUrl = SERVER_API_URL + 'api/custom-student-report-types';

    constructor(private http: HttpClient) {}

    create(customStudentReportType: ICustomStudentReportType): Observable<EntityResponseType> {
        return this.http.post<ICustomStudentReportType>(this.resourceUrl, customStudentReportType, { observe: 'response' });
    }

    update(customStudentReportType: ICustomStudentReportType): Observable<EntityResponseType> {
        return this.http.put<ICustomStudentReportType>(this.resourceUrl, customStudentReportType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICustomStudentReportType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICustomStudentReportType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
