import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PageViewLog } from 'app/shared/model/page-view-log.model';
import { PageViewLogService } from './page-view-log.service';
import { PageViewLogComponent } from './page-view-log.component';
import { PageViewLogDetailComponent } from './page-view-log-detail.component';
import { PageViewLogUpdateComponent } from './page-view-log-update.component';
import { PageViewLogDeletePopupComponent } from './page-view-log-delete-dialog.component';
import { IPageViewLog } from 'app/shared/model/page-view-log.model';

@Injectable({ providedIn: 'root' })
export class PageViewLogResolve implements Resolve<IPageViewLog> {
    constructor(private service: PageViewLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PageViewLog> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PageViewLog>) => response.ok),
                map((pageViewLog: HttpResponse<PageViewLog>) => pageViewLog.body)
            );
        }
        return of(new PageViewLog());
    }
}

export const pageViewLogRoute: Routes = [
    {
        path: 'page-view-log',
        component: PageViewLogComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PageViewLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'page-view-log/:id/view',
        component: PageViewLogDetailComponent,
        resolve: {
            pageViewLog: PageViewLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PageViewLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'page-view-log/new',
        component: PageViewLogUpdateComponent,
        resolve: {
            pageViewLog: PageViewLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PageViewLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'page-view-log/:id/edit',
        component: PageViewLogUpdateComponent,
        resolve: {
            pageViewLog: PageViewLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PageViewLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pageViewLogPopupRoute: Routes = [
    {
        path: 'page-view-log/:id/delete',
        component: PageViewLogDeletePopupComponent,
        resolve: {
            pageViewLog: PageViewLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PageViewLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
