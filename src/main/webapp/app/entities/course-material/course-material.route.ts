import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CourseMaterial } from 'app/shared/model/course-material.model';
import { CourseMaterialService } from './course-material.service';
import { CourseMaterialComponent } from './course-material.component';
import { CourseMaterialDetailComponent } from './course-material-detail.component';
import { CourseMaterialUpdateComponent } from './course-material-update.component';
import { CourseMaterialDeletePopupComponent } from './course-material-delete-dialog.component';
import { ICourseMaterial } from 'app/shared/model/course-material.model';

@Injectable({ providedIn: 'root' })
export class CourseMaterialResolve implements Resolve<ICourseMaterial> {
    constructor(private service: CourseMaterialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CourseMaterial> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CourseMaterial>) => response.ok),
                map((courseMaterial: HttpResponse<CourseMaterial>) => courseMaterial.body)
            );
        }
        return of(new CourseMaterial());
    }
}

export const courseMaterialRoute: Routes = [
    {
        path: 'course-material',
        component: CourseMaterialComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CourseMaterials'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-material/:id/view',
        component: CourseMaterialDetailComponent,
        resolve: {
            courseMaterial: CourseMaterialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterials'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-material/new',
        component: CourseMaterialUpdateComponent,
        resolve: {
            courseMaterial: CourseMaterialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterials'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'course-material/:id/edit',
        component: CourseMaterialUpdateComponent,
        resolve: {
            courseMaterial: CourseMaterialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterials'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courseMaterialPopupRoute: Routes = [
    {
        path: 'course-material/:id/delete',
        component: CourseMaterialDeletePopupComponent,
        resolve: {
            courseMaterial: CourseMaterialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourseMaterials'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
