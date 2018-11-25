import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';

type EntityResponseType = HttpResponse<IMinisterialWorkExperience>;
type EntityArrayResponseType = HttpResponse<IMinisterialWorkExperience[]>;

@Injectable({ providedIn: 'root' })
export class MinisterialWorkExperienceService {
    public resourceUrl = SERVER_API_URL + 'api/ministerial-work-experiences';

    constructor(private http: HttpClient) {}

    create(ministerialWorkExperience: IMinisterialWorkExperience): Observable<EntityResponseType> {
        return this.http.post<IMinisterialWorkExperience>(this.resourceUrl, ministerialWorkExperience, { observe: 'response' });
    }

    update(ministerialWorkExperience: IMinisterialWorkExperience): Observable<EntityResponseType> {
        return this.http.put<IMinisterialWorkExperience>(this.resourceUrl, ministerialWorkExperience, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMinisterialWorkExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMinisterialWorkExperience[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
