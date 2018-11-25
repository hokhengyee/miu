import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourseMaterialAccess } from 'app/shared/model/course-material-access.model';

type EntityResponseType = HttpResponse<ICourseMaterialAccess>;
type EntityArrayResponseType = HttpResponse<ICourseMaterialAccess[]>;

@Injectable({ providedIn: 'root' })
export class CourseMaterialAccessService {
    public resourceUrl = SERVER_API_URL + 'api/course-material-accesses';

    constructor(private http: HttpClient) {}

    create(courseMaterialAccess: ICourseMaterialAccess): Observable<EntityResponseType> {
        return this.http.post<ICourseMaterialAccess>(this.resourceUrl, courseMaterialAccess, { observe: 'response' });
    }

    update(courseMaterialAccess: ICourseMaterialAccess): Observable<EntityResponseType> {
        return this.http.put<ICourseMaterialAccess>(this.resourceUrl, courseMaterialAccess, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourseMaterialAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourseMaterialAccess[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
