import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CourseMaterialAccess } from 'app/shared/model/course-material-access.model';
import { CourseMaterialAccessService } from './course-material-access.service';
import { CourseMaterialAccessComponent } from './course-material-access.component';
import { CourseMaterialAccessDetailComponent } from './course-material-access-detail.component';
import { CourseMaterialAccessUpdateComponent } from './course-material-access-update.component';
import { CourseMaterialAccessDeletePopupComponent } from './course-material-access-delete-dialog.component';
import { ICourseMaterialAccess } from 'app/shared/model/course-material-access.model';

@Injectable({ providedIn: 'root' })
export class CourseMaterialAccessResolve implements Resolve<ICourseMaterialAccess> {
    constructor(private service: CourseMaterialAccessService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CourseMaterialAccess> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CourseMaterialAccess>) => response.ok),
                map((courseMaterialAccess: HttpResponse<CourseMaterialAccess>) => courseMaterialAccess.body)
            );
        }
        return of(new CourseMaterialAccess());
    }
}

export const courseMaterialAccessRoute: Routes = [
    {
        path: 'course-material-access',
        component: CourseMaterialAccessComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CourseMaterialAccesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-material-access/:id/view',
        component: CourseMaterialAccessDetailComponent,
        resolve: {
            courseMaterialAccess: CourseMaterialAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterialAccesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-material-access/new',
        component: CourseMaterialAccessUpdateComponent,
        resolve: {
            courseMaterialAccess: CourseMaterialAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterialAccesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-material-access/:id/edit',
        component: CourseMaterialAccessUpdateComponent,
        resolve: {
            courseMaterialAccess: CourseMaterialAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterialAccesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courseMaterialAccessPopupRoute: Routes = [
    {
        path: 'course-material-access/:id/delete',
        component: CourseMaterialAccessDeletePopupComponent,
        resolve: {
            courseMaterialAccess: CourseMaterialAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterialAccesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
