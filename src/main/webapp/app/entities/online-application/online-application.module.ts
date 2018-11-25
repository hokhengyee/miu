import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    OnlineApplicationComponent,
    OnlineApplicationDetailComponent,
    OnlineApplicationUpdateComponent,
    OnlineApplicationDeletePopupComponent,
    OnlineApplicationDeleteDialogComponent,
    onlineApplicationRoute,
    onlineApplicationPopupRoute
} from './';

const ENTITY_STATES = [...onlineApplicationRoute, ...onlineApplicationPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OnlineApplicationComponent,
        OnlineApplicationDetailComponent,
        OnlineApplicationUpdateComponent,
        OnlineApplicationDeleteDialogComponent,
        OnlineApplicationDeletePopupComponent
    ],
    entryComponents: [
        OnlineApplicationComponent,
        OnlineApplicationUpdateComponent,
        OnlineApplicationDeleteDialogComponent,
        OnlineApplicationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuOnlineApplicationModule {}
