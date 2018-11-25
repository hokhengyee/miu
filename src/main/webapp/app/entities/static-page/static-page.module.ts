import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    StaticPageComponent,
    StaticPageDetailComponent,
    StaticPageUpdateComponent,
    StaticPageDeletePopupComponent,
    StaticPageDeleteDialogComponent,
    staticPageRoute,
    staticPagePopupRoute
} from './';

const ENTITY_STATES = [...staticPageRoute, ...staticPagePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StaticPageComponent,
        StaticPageDetailComponent,
        StaticPageUpdateComponent,
        StaticPageDeleteDialogComponent,
        StaticPageDeletePopupComponent
    ],
    entryComponents: [StaticPageComponent, StaticPageUpdateComponent, StaticPageDeleteDialogComponent, StaticPageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStaticPageModule {}
