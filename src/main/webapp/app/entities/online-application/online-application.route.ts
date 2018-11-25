import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OnlineApplication } from 'app/shared/model/online-application.model';
import { OnlineApplicationService } from './online-application.service';
import { OnlineApplicationComponent } from './online-application.component';
import { OnlineApplicationDetailComponent } from './online-application-detail.component';
import { OnlineApplicationUpdateComponent } from './online-application-update.component';
import { OnlineApplicationDeletePopupComponent } from './online-application-delete-dialog.component';
import { IOnlineApplication } from 'app/shared/model/online-application.model';

@Injectable({ providedIn: 'root' })
export class OnlineApplicationResolve implements Resolve<IOnlineApplication> {
    constructor(private service: OnlineApplicationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OnlineApplication> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OnlineApplication>) => response.ok),
                map((onlineApplication: HttpResponse<OnlineApplication>) => onlineApplication.body)
            );
        }
        return of(new OnlineApplication());
    }
}

export const onlineApplicationRoute: Routes = [
    {
        path: 'online-application',
        component: OnlineApplicationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'OnlineApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'online-application/:id/view',
        component: OnlineApplicationDetailComponent,
        resolve: {
            onlineApplication: OnlineApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OnlineApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'online-application/new',
        component: OnlineApplicationUpdateComponent,
        resolve: {
            onlineApplication: OnlineApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OnlineApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'online-application/:id/edit',
        component: OnlineApplicationUpdateComponent,
        resolve: {
            onlineApplication: OnlineApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OnlineApplications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const onlineApplicationPopupRoute: Routes = [
    {
        path: 'online-application/:id/delete',
        component: OnlineApplicationDeletePopupComponent,
        resolve: {
            onlineApplication: OnlineApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OnlineApplications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
