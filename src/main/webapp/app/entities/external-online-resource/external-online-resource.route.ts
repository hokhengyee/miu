import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExternalOnlineResource } from 'app/shared/model/external-online-resource.model';
import { ExternalOnlineResourceService } from './external-online-resource.service';
import { ExternalOnlineResourceComponent } from './external-online-resource.component';
import { ExternalOnlineResourceDetailComponent } from './external-online-resource-detail.component';
import { ExternalOnlineResourceUpdateComponent } from './external-online-resource-update.component';
import { ExternalOnlineResourceDeletePopupComponent } from './external-online-resource-delete-dialog.component';
import { IExternalOnlineResource } from 'app/shared/model/external-online-resource.model';

@Injectable({ providedIn: 'root' })
export class ExternalOnlineResourceResolve implements Resolve<IExternalOnlineResource> {
    constructor(private service: ExternalOnlineResourceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ExternalOnlineResource> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExternalOnlineResource>) => response.ok),
                map((externalOnlineResource: HttpResponse<ExternalOnlineResource>) => externalOnlineResource.body)
            );
        }
        return of(new ExternalOnlineResource());
    }
}

export const externalOnlineResourceRoute: Routes = [
    {
        path: 'external-online-resource',
        component: ExternalOnlineResourceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ExternalOnlineResources'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'external-online-resource/:id/view',
        component: ExternalOnlineResourceDetailComponent,
        resolve: {
            externalOnlineResource: ExternalOnlineResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalOnlineResources'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'external-online-resource/new',
        component: ExternalOnlineResourceUpdateComponent,
        resolve: {
            externalOnlineResource: ExternalOnlineResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalOnlineResources'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'external-online-resource/:id/edit',
        component: ExternalOnlineResourceUpdateComponent,
        resolve: {
            externalOnlineResource: ExternalOnlineResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalOnlineResources'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const externalOnlineResourcePopupRoute: Routes = [
    {
        path: 'external-online-resource/:id/delete',
        component: ExternalOnlineResourceDeletePopupComponent,
        resolve: {
            externalOnlineResource: ExternalOnlineResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExternalOnlineResources'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
