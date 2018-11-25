import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StaticPageType } from 'app/shared/model/static-page-type.model';
import { StaticPageTypeService } from './static-page-type.service';
import { StaticPageTypeComponent } from './static-page-type.component';
import { StaticPageTypeDetailComponent } from './static-page-type-detail.component';
import { StaticPageTypeUpdateComponent } from './static-page-type-update.component';
import { StaticPageTypeDeletePopupComponent } from './static-page-type-delete-dialog.component';
import { IStaticPageType } from 'app/shared/model/static-page-type.model';

@Injectable({ providedIn: 'root' })
export class StaticPageTypeResolve implements Resolve<IStaticPageType> {
    constructor(private service: StaticPageTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StaticPageType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StaticPageType>) => response.ok),
                map((staticPageType: HttpResponse<StaticPageType>) => staticPageType.body)
            );
        }
        return of(new StaticPageType());
    }
}

export const staticPageTypeRoute: Routes = [
    {
        path: 'static-page-type',
        component: StaticPageTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StaticPageTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'static-page-type/:id/view',
        component: StaticPageTypeDetailComponent,
        resolve: {
            staticPageType: StaticPageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPageTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'static-page-type/new',
        component: StaticPageTypeUpdateComponent,
        resolve: {
            staticPageType: StaticPageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPageTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'static-page-type/:id/edit',
        component: StaticPageTypeUpdateComponent,
        resolve: {
            staticPageType: StaticPageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPageTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const staticPageTypePopupRoute: Routes = [
    {
        path: 'static-page-type/:id/delete',
        component: StaticPageTypeDeletePopupComponent,
        resolve: {
            staticPageType: StaticPageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPageTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
