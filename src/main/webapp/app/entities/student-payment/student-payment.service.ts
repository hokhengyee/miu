import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentPayment } from 'app/shared/model/student-payment.model';

type EntityResponseType = HttpResponse<IStudentPayment>;
type EntityArrayResponseType = HttpResponse<IStudentPayment[]>;

@Injectable({ providedIn: 'root' })
export class StudentPaymentService {
    public resourceUrl = SERVER_API_URL + 'api/student-payments';

    constructor(private http: HttpClient) {}

    create(studentPayment: IStudentPayment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentPayment);
        return this.http
            .post<IStudentPayment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(studentPayment: IStudentPayment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentPayment);
        return this.http
            .put<IStudentPayment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStudentPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentPayment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(studentPayment: IStudentPayment): IStudentPayment {
        const copy: IStudentPayment = Object.assign({}, studentPayment, {
            createdDate:
                studentPayment.createdDate != null && studentPayment.createdDate.isValid() ? studentPayment.createdDate.toJSON() : null,
            paymentDate:
                studentPayment.paymentDate != null && studentPayment.paymentDate.isValid()
                    ? studentPayment.paymentDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.paymentDate = res.body.paymentDate != null ? moment(res.body.paymentDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((studentPayment: IStudentPayment) => {
                studentPayment.createdDate = studentPayment.createdDate != null ? moment(studentPayment.createdDate) : null;
                studentPayment.paymentDate = studentPayment.paymentDate != null ? moment(studentPayment.paymentDate) : null;
            });
        }
        return res;
    }
}
