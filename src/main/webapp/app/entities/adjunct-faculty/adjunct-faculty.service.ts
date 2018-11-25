import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';

type EntityResponseType = HttpResponse<IAdjunctFaculty>;
type EntityArrayResponseType = HttpResponse<IAdjunctFaculty[]>;

@Injectable({ providedIn: 'root' })
export class AdjunctFacultyService {
    public resourceUrl = SERVER_API_URL + 'api/adjunct-faculties';

    constructor(private http: HttpClient) {}

    create(adjunctFaculty: IAdjunctFaculty): Observable<EntityResponseType> {
        return this.http.post<IAdjunctFaculty>(this.resourceUrl, adjunctFaculty, { observe: 'response' });
    }

    update(adjunctFaculty: IAdjunctFaculty): Observable<EntityResponseType> {
        return this.http.put<IAdjunctFaculty>(this.resourceUrl, adjunctFaculty, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdjunctFaculty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdjunctFaculty[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
