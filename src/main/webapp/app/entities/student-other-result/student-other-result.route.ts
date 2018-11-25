import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentOtherResult } from 'app/shared/model/student-other-result.model';
import { StudentOtherResultService } from './student-other-result.service';
import { StudentOtherResultComponent } from './student-other-result.component';
import { StudentOtherResultDetailComponent } from './student-other-result-detail.component';
import { StudentOtherResultUpdateComponent } from './student-other-result-update.component';
import { StudentOtherResultDeletePopupComponent } from './student-other-result-delete-dialog.component';
import { IStudentOtherResult } from 'app/shared/model/student-other-result.model';

@Injectable({ providedIn: 'root' })
export class StudentOtherResultResolve implements Resolve<IStudentOtherResult> {
    constructor(private service: StudentOtherResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StudentOtherResult> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentOtherResult>) => response.ok),
                map((studentOtherResult: HttpResponse<StudentOtherResult>) => studentOtherResult.body)
            );
        }
        return of(new StudentOtherResult());
    }
}

export const studentOtherResultRoute: Routes = [
    {
        path: 'student-other-result',
        component: StudentOtherResultComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StudentOtherResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-other-result/:id/view',
        component: StudentOtherResultDetailComponent,
        resolve: {
            studentOtherResult: StudentOtherResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentOtherResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-other-result/new',
        component: StudentOtherResultUpdateComponent,
        resolve: {
            studentOtherResult: StudentOtherResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentOtherResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-other-result/:id/edit',
        component: StudentOtherResultUpdateComponent,
        resolve: {
            studentOtherResult: StudentOtherResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentOtherResults'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentOtherResultPopupRoute: Routes = [
    {
        path: 'student-other-result/:id/delete',
        component: StudentOtherResultDeletePopupComponent,
        resolve: {
            studentOtherResult: StudentOtherResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentOtherResults'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
