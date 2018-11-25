import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourseMaterial } from 'app/shared/model/course-material.model';

type EntityResponseType = HttpResponse<ICourseMaterial>;
type EntityArrayResponseType = HttpResponse<ICourseMaterial[]>;

@Injectable({ providedIn: 'root' })
export class CourseMaterialService {
    public resourceUrl = SERVER_API_URL + 'api/course-materials';

    constructor(private http: HttpClient) {}

    create(courseMaterial: ICourseMaterial): Observable<EntityResponseType> {
        return this.http.post<ICourseMaterial>(this.resourceUrl, courseMaterial, { observe: 'response' });
    }

    update(courseMaterial: ICourseMaterial): Observable<EntityResponseType> {
        return this.http.put<ICourseMaterial>(this.resourceUrl, courseMaterial, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourseMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourseMaterial[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
