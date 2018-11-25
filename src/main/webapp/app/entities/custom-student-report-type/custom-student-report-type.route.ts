import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';
import { CustomStudentReportTypeService } from './custom-student-report-type.service';
import { CustomStudentReportTypeComponent } from './custom-student-report-type.component';
import { CustomStudentReportTypeDetailComponent } from './custom-student-report-type-detail.component';
import { CustomStudentReportTypeUpdateComponent } from './custom-student-report-type-update.component';
import { CustomStudentReportTypeDeletePopupComponent } from './custom-student-report-type-delete-dialog.component';
import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';

@Injectable({ providedIn: 'root' })
export class CustomStudentReportTypeResolve implements Resolve<ICustomStudentReportType> {
    constructor(private service: CustomStudentReportTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CustomStudentReportType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CustomStudentReportType>) => response.ok),
                map((customStudentReportType: HttpResponse<CustomStudentReportType>) => customStudentReportType.body)
            );
        }
        return of(new CustomStudentReportType());
    }
}

export const customStudentReportTypeRoute: Routes = [
    {
        path: 'custom-student-report-type',
        component: CustomStudentReportTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CustomStudentReportTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'custom-student-report-type/:id/view',
        component: CustomStudentReportTypeDetailComponent,
        resolve: {
            customStudentReportType: CustomStudentReportTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CustomStudentReportTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'custom-student-report-type/new',
        component: CustomStudentReportTypeUpdateComponent,
        resolve: {
            customStudentReportType: CustomStudentReportTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CustomStudentReportTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'custom-student-report-type/:id/edit',
        component: CustomStudentReportTypeUpdateComponent,
        resolve: {
            customStudentReportType: CustomStudentReportTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CustomStudentReportTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customStudentReportTypePopupRoute: Routes = [
    {
        path: 'custom-student-report-type/:id/delete',
        component: CustomStudentReportTypeDeletePopupComponent,
        resolve: {
            customStudentReportType: CustomStudentReportTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CustomStudentReportTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
