import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RecordOfCertificate } from 'app/shared/model/record-of-certificate.model';
import { RecordOfCertificateService } from './record-of-certificate.service';
import { RecordOfCertificateComponent } from './record-of-certificate.component';
import { RecordOfCertificateDetailComponent } from './record-of-certificate-detail.component';
import { RecordOfCertificateUpdateComponent } from './record-of-certificate-update.component';
import { RecordOfCertificateDeletePopupComponent } from './record-of-certificate-delete-dialog.component';
import { IRecordOfCertificate } from 'app/shared/model/record-of-certificate.model';

@Injectable({ providedIn: 'root' })
export class RecordOfCertificateResolve implements Resolve<IRecordOfCertificate> {
    constructor(private service: RecordOfCertificateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RecordOfCertificate> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RecordOfCertificate>) => response.ok),
                map((recordOfCertificate: HttpResponse<RecordOfCertificate>) => recordOfCertificate.body)
            );
        }
        return of(new RecordOfCertificate());
    }
}

export const recordOfCertificateRoute: Routes = [
    {
        path: 'record-of-certificate',
        component: RecordOfCertificateComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RecordOfCertificates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-of-certificate/:id/view',
        component: RecordOfCertificateDetailComponent,
        resolve: {
            recordOfCertificate: RecordOfCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordOfCertificates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-of-certificate/new',
        component: RecordOfCertificateUpdateComponent,
        resolve: {
            recordOfCertificate: RecordOfCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordOfCertificates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'record-of-certificate/:id/edit',
        component: RecordOfCertificateUpdateComponent,
        resolve: {
            recordOfCertificate: RecordOfCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordOfCertificates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recordOfCertificatePopupRoute: Routes = [
    {
        path: 'record-of-certificate/:id/delete',
        component: RecordOfCertificateDeletePopupComponent,
        resolve: {
            recordOfCertificate: RecordOfCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordOfCertificates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
