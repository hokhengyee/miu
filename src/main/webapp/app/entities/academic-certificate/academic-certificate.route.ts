import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AcademicCertificate } from 'app/shared/model/academic-certificate.model';
import { AcademicCertificateService } from './academic-certificate.service';
import { AcademicCertificateComponent } from './academic-certificate.component';
import { AcademicCertificateDetailComponent } from './academic-certificate-detail.component';
import { AcademicCertificateUpdateComponent } from './academic-certificate-update.component';
import { AcademicCertificateDeletePopupComponent } from './academic-certificate-delete-dialog.component';
import { IAcademicCertificate } from 'app/shared/model/academic-certificate.model';

@Injectable({ providedIn: 'root' })
export class AcademicCertificateResolve implements Resolve<IAcademicCertificate> {
    constructor(private service: AcademicCertificateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AcademicCertificate> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AcademicCertificate>) => response.ok),
                map((academicCertificate: HttpResponse<AcademicCertificate>) => academicCertificate.body)
            );
        }
        return of(new AcademicCertificate());
    }
}

export const academicCertificateRoute: Routes = [
    {
        path: 'academic-certificate',
        component: AcademicCertificateComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AcademicCertificates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'academic-certificate/:id/view',
        component: AcademicCertificateDetailComponent,
        resolve: {
            academicCertificate: AcademicCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicCertificates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'academic-certificate/new',
        component: AcademicCertificateUpdateComponent,
        resolve: {
            academicCertificate: AcademicCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicCertificates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'academic-certificate/:id/edit',
        component: AcademicCertificateUpdateComponent,
        resolve: {
            academicCertificate: AcademicCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicCertificates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const academicCertificatePopupRoute: Routes = [
    {
        path: 'academic-certificate/:id/delete',
        component: AcademicCertificateDeletePopupComponent,
        resolve: {
            academicCertificate: AcademicCertificateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicCertificates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
