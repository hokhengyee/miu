import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExternalOnlineResource } from 'app/shared/model/external-online-resource.model';

type EntityResponseType = HttpResponse<IExternalOnlineResource>;
type EntityArrayResponseType = HttpResponse<IExternalOnlineResource[]>;

@Injectable({ providedIn: 'root' })
export class ExternalOnlineResourceService {
    public resourceUrl = SERVER_API_URL + 'api/external-online-resources';

    constructor(private http: HttpClient) {}

    create(externalOnlineResource: IExternalOnlineResource): Observable<EntityResponseType> {
        return this.http.post<IExternalOnlineResource>(this.resourceUrl, externalOnlineResource, { observe: 'response' });
    }

    update(externalOnlineResource: IExternalOnlineResource): Observable<EntityResponseType> {
        return this.http.put<IExternalOnlineResource>(this.resourceUrl, externalOnlineResource, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExternalOnlineResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExternalOnlineResource[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
