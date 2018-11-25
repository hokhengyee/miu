import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    ForumRoomComponent,
    ForumRoomDetailComponent,
    ForumRoomUpdateComponent,
    ForumRoomDeletePopupComponent,
    ForumRoomDeleteDialogComponent,
    forumRoomRoute,
    forumRoomPopupRoute
} from './';

const ENTITY_STATES = [...forumRoomRoute, ...forumRoomPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ForumRoomComponent,
        ForumRoomDetailComponent,
        ForumRoomUpdateComponent,
        ForumRoomDeleteDialogComponent,
        ForumRoomDeletePopupComponent
    ],
    entryComponents: [ForumRoomComponent, ForumRoomUpdateComponent, ForumRoomDeleteDialogComponent, ForumRoomDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuForumRoomModule {}
