import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourseAccess } from 'app/shared/model/course-access.model';

type EntityResponseType = HttpResponse<ICourseAccess>;
type EntityArrayResponseType = HttpResponse<ICourseAccess[]>;

@Injectable({ providedIn: 'root' })
export class CourseAccessService {
    public resourceUrl = SERVER_API_URL + 'api/course-accesses';

    constructor(private http: HttpClient) {}

    create(courseAccess: ICourseAccess): Observable<EntityResponseType> {
        return this.http.post<ICourseAccess>(this.resourceUrl, courseAccess, { observe: 'response' });
    }

    update(courseAccess: ICourseAccess): Observable<EntityResponseType> {
        return this.http.put<ICourseAccess>(this.resourceUrl, courseAccess, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourseAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourseAccess[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
