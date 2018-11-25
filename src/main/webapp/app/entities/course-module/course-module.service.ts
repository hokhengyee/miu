import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourseModule } from 'app/shared/model/course-module.model';

type EntityResponseType = HttpResponse<ICourseModule>;
type EntityArrayResponseType = HttpResponse<ICourseModule[]>;

@Injectable({ providedIn: 'root' })
export class CourseModuleService {
    public resourceUrl = SERVER_API_URL + 'api/course-modules';

    constructor(private http: HttpClient) {}

    create(courseModule: ICourseModule): Observable<EntityResponseType> {
        return this.http.post<ICourseModule>(this.resourceUrl, courseModule, { observe: 'response' });
    }

    update(courseModule: ICourseModule): Observable<EntityResponseType> {
        return this.http.put<ICourseModule>(this.resourceUrl, courseModule, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourseModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourseModule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
