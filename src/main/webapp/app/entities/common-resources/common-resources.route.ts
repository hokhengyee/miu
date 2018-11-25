import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommonResources } from 'app/shared/model/common-resources.model';
import { CommonResourcesService } from './common-resources.service';
import { CommonResourcesComponent } from './common-resources.component';
import { CommonResourcesDetailComponent } from './common-resources-detail.component';
import { CommonResourcesUpdateComponent } from './common-resources-update.component';
import { CommonResourcesDeletePopupComponent } from './common-resources-delete-dialog.component';
import { ICommonResources } from 'app/shared/model/common-resources.model';

@Injectable({ providedIn: 'root' })
export class CommonResourcesResolve implements Resolve<ICommonResources> {
    constructor(private service: CommonResourcesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CommonResources> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommonResources>) => response.ok),
                map((commonResources: HttpResponse<CommonResources>) => commonResources.body)
            );
        }
        return of(new CommonResources());
    }
}

export const commonResourcesRoute: Routes = [
    {
        path: 'common-resources',
        component: CommonResourcesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CommonResources'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'common-resources/:id/view',
        component: CommonResourcesDetailComponent,
        resolve: {
            commonResources: CommonResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommonResources'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'common-resources/new',
        component: CommonResourcesUpdateComponent,
        resolve: {
            commonResources: CommonResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommonResources'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'common-resources/:id/edit',
        component: CommonResourcesUpdateComponent,
        resolve: {
            commonResources: CommonResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommonResources'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commonResourcesPopupRoute: Routes = [
    {
        path: 'common-resources/:id/delete',
        component: CommonResourcesDeletePopupComponent,
        resolve: {
            commonResources: CommonResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommonResources'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
