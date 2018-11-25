import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    NewsAndEventComponent,
    NewsAndEventDetailComponent,
    NewsAndEventUpdateComponent,
    NewsAndEventDeletePopupComponent,
    NewsAndEventDeleteDialogComponent,
    newsAndEventRoute,
    newsAndEventPopupRoute
} from './';

const ENTITY_STATES = [...newsAndEventRoute, ...newsAndEventPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NewsAndEventComponent,
        NewsAndEventDetailComponent,
        NewsAndEventUpdateComponent,
        NewsAndEventDeleteDialogComponent,
        NewsAndEventDeletePopupComponent
    ],
    entryComponents: [
        NewsAndEventComponent,
        NewsAndEventUpdateComponent,
        NewsAndEventDeleteDialogComponent,
        NewsAndEventDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuNewsAndEventModule {}
