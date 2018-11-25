import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    ForumRoomMessageComponent,
    ForumRoomMessageDetailComponent,
    ForumRoomMessageUpdateComponent,
    ForumRoomMessageDeletePopupComponent,
    ForumRoomMessageDeleteDialogComponent,
    forumRoomMessageRoute,
    forumRoomMessagePopupRoute
} from './';

const ENTITY_STATES = [...forumRoomMessageRoute, ...forumRoomMessagePopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ForumRoomMessageComponent,
        ForumRoomMessageDetailComponent,
        ForumRoomMessageUpdateComponent,
        ForumRoomMessageDeleteDialogComponent,
        ForumRoomMessageDeletePopupComponent
    ],
    entryComponents: [
        ForumRoomMessageComponent,
        ForumRoomMessageUpdateComponent,
        ForumRoomMessageDeleteDialogComponent,
        ForumRoomMessageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuForumRoomMessageModule {}
