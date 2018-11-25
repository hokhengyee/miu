import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';

type EntityResponseType = HttpResponse<ILecturerProfile>;
type EntityArrayResponseType = HttpResponse<ILecturerProfile[]>;

@Injectable({ providedIn: 'root' })
export class LecturerProfileService {
    public resourceUrl = SERVER_API_URL + 'api/lecturer-profiles';

    constructor(private http: HttpClient) {}

    create(lecturerProfile: ILecturerProfile): Observable<EntityResponseType> {
        return this.http.post<ILecturerProfile>(this.resourceUrl, lecturerProfile, { observe: 'response' });
    }

    update(lecturerProfile: ILecturerProfile): Observable<EntityResponseType> {
        return this.http.put<ILecturerProfile>(this.resourceUrl, lecturerProfile, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILecturerProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILecturerProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
