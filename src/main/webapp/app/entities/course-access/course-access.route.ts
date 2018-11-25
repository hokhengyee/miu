import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CourseAccess } from 'app/shared/model/course-access.model';
import { CourseAccessService } from './course-access.service';
import { CourseAccessComponent } from './course-access.component';
import { CourseAccessDetailComponent } from './course-access-detail.component';
import { CourseAccessUpdateComponent } from './course-access-update.component';
import { CourseAccessDeletePopupComponent } from './course-access-delete-dialog.component';
import { ICourseAccess } from 'app/shared/model/course-access.model';

@Injectable({ providedIn: 'root' })
export class CourseAccessResolve implements Resolve<ICourseAccess> {
    constructor(private service: CourseAccessService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CourseAccess> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CourseAccess>) => response.ok),
                map((courseAccess: HttpResponse<CourseAccess>) => courseAccess.body)
            );
        }
        return of(new CourseAccess());
    }
}

export const courseAccessRoute: Routes = [
    {
        path: 'course-access',
        component: CourseAccessComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CourseAccesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-access/:id/view',
        component: CourseAccessDetailComponent,
        resolve: {
            courseAccess: CourseAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseAccesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-access/new',
        component: CourseAccessUpdateComponent,
        resolve: {
            courseAccess: CourseAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseAccesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-access/:id/edit',
        component: CourseAccessUpdateComponent,
        resolve: {
            courseAccess: CourseAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseAccesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courseAccessPopupRoute: Routes = [
    {
        path: 'course-access/:id/delete',
        component: CourseAccessDeletePopupComponent,
        resolve: {
            courseAccess: CourseAccessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseAccesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
