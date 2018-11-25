import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CourseModule } from 'app/shared/model/course-module.model';
import { CourseModuleService } from './course-module.service';
import { CourseModuleComponent } from './course-module.component';
import { CourseModuleDetailComponent } from './course-module-detail.component';
import { CourseModuleUpdateComponent } from './course-module-update.component';
import { CourseModuleDeletePopupComponent } from './course-module-delete-dialog.component';
import { ICourseModule } from 'app/shared/model/course-module.model';

@Injectable({ providedIn: 'root' })
export class CourseModuleResolve implements Resolve<ICourseModule> {
    constructor(private service: CourseModuleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CourseModule> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CourseModule>) => response.ok),
                map((courseModule: HttpResponse<CourseModule>) => courseModule.body)
            );
        }
        return of(new CourseModule());
    }
}

export const courseModuleRoute: Routes = [
    {
        path: 'course-module',
        component: CourseModuleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CourseModules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-module/:id/view',
        component: CourseModuleDetailComponent,
        resolve: {
            courseModule: CourseModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseModules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-module/new',
        component: CourseModuleUpdateComponent,
        resolve: {
            courseModule: CourseModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseModules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-module/:id/edit',
        component: CourseModuleUpdateComponent,
        resolve: {
            courseModule: CourseModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseModules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courseModulePopupRoute: Routes = [
    {
        path: 'course-module/:id/delete',
        component: CourseModuleDeletePopupComponent,
        resolve: {
            courseModule: CourseModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseModules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
