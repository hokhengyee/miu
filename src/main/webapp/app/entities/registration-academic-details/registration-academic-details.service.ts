import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';

type EntityResponseType = HttpResponse<IRegistrationAcademicDetails>;
type EntityArrayResponseType = HttpResponse<IRegistrationAcademicDetails[]>;

@Injectable({ providedIn: 'root' })
export class RegistrationAcademicDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/registration-academic-details';

    constructor(private http: HttpClient) {}

    create(registrationAcademicDetails: IRegistrationAcademicDetails): Observable<EntityResponseType> {
        return this.http.post<IRegistrationAcademicDetails>(this.resourceUrl, registrationAcademicDetails, { observe: 'response' });
    }

    update(registrationAcademicDetails: IRegistrationAcademicDetails): Observable<EntityResponseType> {
        return this.http.put<IRegistrationAcademicDetails>(this.resourceUrl, registrationAcademicDetails, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegistrationAcademicDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegistrationAcademicDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
