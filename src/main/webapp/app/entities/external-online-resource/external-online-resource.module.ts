import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    ExternalOnlineResourceComponent,
    ExternalOnlineResourceDetailComponent,
    ExternalOnlineResourceUpdateComponent,
    ExternalOnlineResourceDeletePopupComponent,
    ExternalOnlineResourceDeleteDialogComponent,
    externalOnlineResourceRoute,
    externalOnlineResourcePopupRoute
} from './';

const ENTITY_STATES = [...externalOnlineResourceRoute, ...externalOnlineResourcePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExternalOnlineResourceComponent,
        ExternalOnlineResourceDetailComponent,
        ExternalOnlineResourceUpdateComponent,
        ExternalOnlineResourceDeleteDialogComponent,
        ExternalOnlineResourceDeletePopupComponent
    ],
    entryComponents: [
        ExternalOnlineResourceComponent,
        ExternalOnlineResourceUpdateComponent,
        ExternalOnlineResourceDeleteDialogComponent,
        ExternalOnlineResourceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuExternalOnlineResourceModule {}
