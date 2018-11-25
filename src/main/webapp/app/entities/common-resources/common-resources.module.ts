import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    CommonResourcesComponent,
    CommonResourcesDetailComponent,
    CommonResourcesUpdateComponent,
    CommonResourcesDeletePopupComponent,
    CommonResourcesDeleteDialogComponent,
    commonResourcesRoute,
    commonResourcesPopupRoute
} from './';

const ENTITY_STATES = [...commonResourcesRoute, ...commonResourcesPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommonResourcesComponent,
        CommonResourcesDetailComponent,
        CommonResourcesUpdateComponent,
        CommonResourcesDeleteDialogComponent,
        CommonResourcesDeletePopupComponent
    ],
    entryComponents: [
        CommonResourcesComponent,
        CommonResourcesUpdateComponent,
        CommonResourcesDeleteDialogComponent,
        CommonResourcesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuCommonResourcesModule {}
