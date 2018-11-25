import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentModuleResult } from 'app/shared/model/student-module-result.model';
import { StudentModuleResultService } from './student-module-result.service';
import { StudentModuleResultComponent } from './student-module-result.component';
import { StudentModuleResultDetailComponent } from './student-module-result-detail.component';
import { StudentModuleResultUpdateComponent } from './student-module-result-update.component';
import { StudentModuleResultDeletePopupComponent } from './student-module-result-delete-dialog.component';
import { IStudentModuleResult } from 'app/shared/model/student-module-result.model';

@Injectable({ providedIn: 'root' })
export class StudentModuleResultResolve implements Resolve<IStudentModuleResult> {
    constructor(private service: StudentModuleResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StudentModuleResult> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentModuleResult>) => response.ok),
                map((studentModuleResult: HttpResponse<StudentModuleResult>) => studentModuleResult.body)
            );
        }
        return of(new StudentModuleResult());
    }
}

export const studentModuleResultRoute: Routes = [
    {
        path: 'student-module-result',
        component: StudentModuleResultComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StudentModuleResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-module-result/:id/view',
        component: StudentModuleResultDetailComponent,
        resolve: {
            studentModuleResult: StudentModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentModuleResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-module-result/new',
        component: StudentModuleResultUpdateComponent,
        resolve: {
            studentModuleResult: StudentModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentModuleResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-module-result/:id/edit',
        component: StudentModuleResultUpdateComponent,
        resolve: {
            studentModuleResult: StudentModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentModuleResults'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentModuleResultPopupRoute: Routes = [
    {
        path: 'student-module-result/:id/delete',
        component: StudentModuleResultDeletePopupComponent,
        resolve: {
            studentModuleResult: StudentModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentModuleResults'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
