import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntryQualification } from 'app/shared/model/entry-qualification.model';

type EntityResponseType = HttpResponse<IEntryQualification>;
type EntityArrayResponseType = HttpResponse<IEntryQualification[]>;

@Injectable({ providedIn: 'root' })
export class EntryQualificationService {
    public resourceUrl = SERVER_API_URL + 'api/entry-qualifications';

    constructor(private http: HttpClient) {}

    create(entryQualification: IEntryQualification): Observable<EntityResponseType> {
        return this.http.post<IEntryQualification>(this.resourceUrl, entryQualification, { observe: 'response' });
    }

    update(entryQualification: IEntryQualification): Observable<EntityResponseType> {
        return this.http.put<IEntryQualification>(this.resourceUrl, entryQualification, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEntryQualification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEntryQualification[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
