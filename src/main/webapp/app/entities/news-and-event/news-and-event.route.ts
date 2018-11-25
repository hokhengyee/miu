import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NewsAndEvent } from 'app/shared/model/news-and-event.model';
import { NewsAndEventService } from './news-and-event.service';
import { NewsAndEventComponent } from './news-and-event.component';
import { NewsAndEventDetailComponent } from './news-and-event-detail.component';
import { NewsAndEventUpdateComponent } from './news-and-event-update.component';
import { NewsAndEventDeletePopupComponent } from './news-and-event-delete-dialog.component';
import { INewsAndEvent } from 'app/shared/model/news-and-event.model';

@Injectable({ providedIn: 'root' })
export class NewsAndEventResolve implements Resolve<INewsAndEvent> {
    constructor(private service: NewsAndEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<NewsAndEvent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NewsAndEvent>) => response.ok),
                map((newsAndEvent: HttpResponse<NewsAndEvent>) => newsAndEvent.body)
            );
        }
        return of(new NewsAndEvent());
    }
}

export const newsAndEventRoute: Routes = [
    {
        path: 'news-and-event',
        component: NewsAndEventComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'NewsAndEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'news-and-event/:id/view',
        component: NewsAndEventDetailComponent,
        resolve: {
            newsAndEvent: NewsAndEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NewsAndEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'news-and-event/new',
        component: NewsAndEventUpdateComponent,
        resolve: {
            newsAndEvent: NewsAndEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NewsAndEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'news-and-event/:id/edit',
        component: NewsAndEventUpdateComponent,
        resolve: {
            newsAndEvent: NewsAndEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NewsAndEvents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const newsAndEventPopupRoute: Routes = [
    {
        path: 'news-and-event/:id/delete',
        component: NewsAndEventDeletePopupComponent,
        resolve: {
            newsAndEvent: NewsAndEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NewsAndEvents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
