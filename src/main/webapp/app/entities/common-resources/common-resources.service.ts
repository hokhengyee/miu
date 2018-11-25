import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommonResources } from 'app/shared/model/common-resources.model';

type EntityResponseType = HttpResponse<ICommonResources>;
type EntityArrayResponseType = HttpResponse<ICommonResources[]>;

@Injectable({ providedIn: 'root' })
export class CommonResourcesService {
    public resourceUrl = SERVER_API_URL + 'api/common-resources';

    constructor(private http: HttpClient) {}

    create(commonResources: ICommonResources): Observable<EntityResponseType> {
        return this.http.post<ICommonResources>(this.resourceUrl, commonResources, { observe: 'response' });
    }

    update(commonResources: ICommonResources): Observable<EntityResponseType> {
        return this.http.put<ICommonResources>(this.resourceUrl, commonResources, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICommonResources>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICommonResources[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
