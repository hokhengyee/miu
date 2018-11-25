import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAcademicCertificate } from 'app/shared/model/academic-certificate.model';

type EntityResponseType = HttpResponse<IAcademicCertificate>;
type EntityArrayResponseType = HttpResponse<IAcademicCertificate[]>;

@Injectable({ providedIn: 'root' })
export class AcademicCertificateService {
    public resourceUrl = SERVER_API_URL + 'api/academic-certificates';

    constructor(private http: HttpClient) {}

    create(academicCertificate: IAcademicCertificate): Observable<EntityResponseType> {
        return this.http.post<IAcademicCertificate>(this.resourceUrl, academicCertificate, { observe: 'response' });
    }

    update(academicCertificate: IAcademicCertificate): Observable<EntityResponseType> {
        return this.http.put<IAcademicCertificate>(this.resourceUrl, academicCertificate, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAcademicCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAcademicCertificate[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
