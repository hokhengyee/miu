import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalutation } from 'app/shared/model/salutation.model';

type EntityResponseType = HttpResponse<ISalutation>;
type EntityArrayResponseType = HttpResponse<ISalutation[]>;

@Injectable({ providedIn: 'root' })
export class SalutationService {
    public resourceUrl = SERVER_API_URL + 'api/salutations';

    constructor(private http: HttpClient) {}

    create(salutation: ISalutation): Observable<EntityResponseType> {
        return this.http.post<ISalutation>(this.resourceUrl, salutation, { observe: 'response' });
    }

    update(salutation: ISalutation): Observable<EntityResponseType> {
        return this.http.put<ISalutation>(this.resourceUrl, salutation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISalutation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalutation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
