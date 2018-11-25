import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentPayment } from 'app/shared/model/student-payment.model';
import { StudentPaymentService } from './student-payment.service';
import { StudentPaymentComponent } from './student-payment.component';
import { StudentPaymentDetailComponent } from './student-payment-detail.component';
import { StudentPaymentUpdateComponent } from './student-payment-update.component';
import { StudentPaymentDeletePopupComponent } from './student-payment-delete-dialog.component';
import { IStudentPayment } from 'app/shared/model/student-payment.model';

@Injectable({ providedIn: 'root' })
export class StudentPaymentResolve implements Resolve<IStudentPayment> {
    constructor(private service: StudentPaymentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StudentPayment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentPayment>) => response.ok),
                map((studentPayment: HttpResponse<StudentPayment>) => studentPayment.body)
            );
        }
        return of(new StudentPayment());
    }
}

export const studentPaymentRoute: Routes = [
    {
        path: 'student-payment',
        component: StudentPaymentComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StudentPayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-payment/:id/view',
        component: StudentPaymentDetailComponent,
        resolve: {
            studentPayment: StudentPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentPayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-payment/new',
        component: StudentPaymentUpdateComponent,
        resolve: {
            studentPayment: StudentPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentPayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-payment/:id/edit',
        component: StudentPaymentUpdateComponent,
        resolve: {
            studentPayment: StudentPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentPayments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentPaymentPopupRoute: Routes = [
    {
        path: 'student-payment/:id/delete',
        component: StudentPaymentDeletePopupComponent,
        resolve: {
            studentPayment: StudentPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
