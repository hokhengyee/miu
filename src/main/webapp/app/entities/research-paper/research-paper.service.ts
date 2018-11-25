import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResearchPaper } from 'app/shared/model/research-paper.model';

type EntityResponseType = HttpResponse<IResearchPaper>;
type EntityArrayResponseType = HttpResponse<IResearchPaper[]>;

@Injectable({ providedIn: 'root' })
export class ResearchPaperService {
    public resourceUrl = SERVER_API_URL + 'api/research-papers';

    constructor(private http: HttpClient) {}

    create(researchPaper: IResearchPaper): Observable<EntityResponseType> {
        return this.http.post<IResearchPaper>(this.resourceUrl, researchPaper, { observe: 'response' });
    }

    update(researchPaper: IResearchPaper): Observable<EntityResponseType> {
        return this.http.put<IResearchPaper>(this.resourceUrl, researchPaper, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResearchPaper>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResearchPaper[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
