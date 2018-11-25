import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ForumRoomMessage } from 'app/shared/model/forum-room-message.model';
import { ForumRoomMessageService } from './forum-room-message.service';
import { ForumRoomMessageComponent } from './forum-room-message.component';
import { ForumRoomMessageDetailComponent } from './forum-room-message-detail.component';
import { ForumRoomMessageUpdateComponent } from './forum-room-message-update.component';
import { ForumRoomMessageDeletePopupComponent } from './forum-room-message-delete-dialog.component';
import { IForumRoomMessage } from 'app/shared/model/forum-room-message.model';

@Injectable({ providedIn: 'root' })
export class ForumRoomMessageResolve implements Resolve<IForumRoomMessage> {
    constructor(private service: ForumRoomMessageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ForumRoomMessage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ForumRoomMessage>) => response.ok),
                map((forumRoomMessage: HttpResponse<ForumRoomMessage>) => forumRoomMessage.body)
            );
        }
        return of(new ForumRoomMessage());
    }
}

export const forumRoomMessageRoute: Routes = [
    {
        path: 'forum-room-message',
        component: ForumRoomMessageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ForumRoomMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forum-room-message/:id/view',
        component: ForumRoomMessageDetailComponent,
        resolve: {
            forumRoomMessage: ForumRoomMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRoomMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forum-room-message/new',
        component: ForumRoomMessageUpdateComponent,
        resolve: {
            forumRoomMessage: ForumRoomMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRoomMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forum-room-message/:id/edit',
        component: ForumRoomMessageUpdateComponent,
        resolve: {
            forumRoomMessage: ForumRoomMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRoomMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const forumRoomMessagePopupRoute: Routes = [
    {
        path: 'forum-room-message/:id/delete',
        component: ForumRoomMessageDeletePopupComponent,
        resolve: {
            forumRoomMessage: ForumRoomMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRoomMessages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
