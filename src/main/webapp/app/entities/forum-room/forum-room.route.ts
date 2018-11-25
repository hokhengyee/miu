import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ForumRoom } from 'app/shared/model/forum-room.model';
import { ForumRoomService } from './forum-room.service';
import { ForumRoomComponent } from './forum-room.component';
import { ForumRoomDetailComponent } from './forum-room-detail.component';
import { ForumRoomUpdateComponent } from './forum-room-update.component';
import { ForumRoomDeletePopupComponent } from './forum-room-delete-dialog.component';
import { IForumRoom } from 'app/shared/model/forum-room.model';

@Injectable({ providedIn: 'root' })
export class ForumRoomResolve implements Resolve<IForumRoom> {
    constructor(private service: ForumRoomService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ForumRoom> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ForumRoom>) => response.ok),
                map((forumRoom: HttpResponse<ForumRoom>) => forumRoom.body)
            );
        }
        return of(new ForumRoom());
    }
}

export const forumRoomRoute: Routes = [
    {
        path: 'forum-room',
        component: ForumRoomComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ForumRooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forum-room/:id/view',
        component: ForumRoomDetailComponent,
        resolve: {
            forumRoom: ForumRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forum-room/new',
        component: ForumRoomUpdateComponent,
        resolve: {
            forumRoom: ForumRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forum-room/:id/edit',
        component: ForumRoomUpdateComponent,
        resolve: {
            forumRoom: ForumRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRooms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const forumRoomPopupRoute: Routes = [
    {
        path: 'forum-room/:id/delete',
        component: ForumRoomDeletePopupComponent,
        resolve: {
            forumRoom: ForumRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ForumRooms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
