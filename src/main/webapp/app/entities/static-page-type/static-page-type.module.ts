import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    StaticPageTypeComponent,
    StaticPageTypeDetailComponent,
    StaticPageTypeUpdateComponent,
    StaticPageTypeDeletePopupComponent,
    StaticPageTypeDeleteDialogComponent,
    staticPageTypeRoute,
    staticPageTypePopupRoute
} from './';

const ENTITY_STATES = [...staticPageTypeRoute, ...staticPageTypePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StaticPageTypeComponent,
        StaticPageTypeDetailComponent,
        StaticPageTypeUpdateComponent,
        StaticPageTypeDeleteDialogComponent,
        StaticPageTypeDeletePopupComponent
    ],
    entryComponents: [
        StaticPageTypeComponent,
        StaticPageTypeUpdateComponent,
        StaticPageTypeDeleteDialogComponent,
        StaticPageTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStaticPageTypeModule {}
