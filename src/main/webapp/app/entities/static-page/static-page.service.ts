import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStaticPage } from 'app/shared/model/static-page.model';

type EntityResponseType = HttpResponse<IStaticPage>;
type EntityArrayResponseType = HttpResponse<IStaticPage[]>;

@Injectable({ providedIn: 'root' })
export class StaticPageService {
    public resourceUrl = SERVER_API_URL + 'api/static-pages';

    constructor(private http: HttpClient) {}

    create(staticPage: IStaticPage): Observable<EntityResponseType> {
        return this.http.post<IStaticPage>(this.resourceUrl, staticPage, { observe: 'response' });
    }

    update(staticPage: IStaticPage): Observable<EntityResponseType> {
        return this.http.put<IStaticPage>(this.resourceUrl, staticPage, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStaticPage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStaticPage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
