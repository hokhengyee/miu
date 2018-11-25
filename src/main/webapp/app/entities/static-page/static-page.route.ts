import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StaticPage } from 'app/shared/model/static-page.model';
import { StaticPageService } from './static-page.service';
import { StaticPageComponent } from './static-page.component';
import { StaticPageDetailComponent } from './static-page-detail.component';
import { StaticPageUpdateComponent } from './static-page-update.component';
import { StaticPageDeletePopupComponent } from './static-page-delete-dialog.component';
import { IStaticPage } from 'app/shared/model/static-page.model';

@Injectable({ providedIn: 'root' })
export class StaticPageResolve implements Resolve<IStaticPage> {
    constructor(private service: StaticPageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StaticPage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StaticPage>) => response.ok),
                map((staticPage: HttpResponse<StaticPage>) => staticPage.body)
            );
        }
        return of(new StaticPage());
    }
}

export const staticPageRoute: Routes = [
    {
        path: 'static-page',
        component: StaticPageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StaticPages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'static-page/:id/view',
        component: StaticPageDetailComponent,
        resolve: {
            staticPage: StaticPageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'static-page/new',
        component: StaticPageUpdateComponent,
        resolve: {
            staticPage: StaticPageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'static-page/:id/edit',
        component: StaticPageUpdateComponent,
        resolve: {
            staticPage: StaticPageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const staticPagePopupRoute: Routes = [
    {
        path: 'static-page/:id/delete',
        component: StaticPageDeletePopupComponent,
        resolve: {
            staticPage: StaticPageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StaticPages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
