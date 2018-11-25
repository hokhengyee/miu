import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    SalutationComponent,
    SalutationDetailComponent,
    SalutationUpdateComponent,
    SalutationDeletePopupComponent,
    SalutationDeleteDialogComponent,
    salutationRoute,
    salutationPopupRoute
} from './';

const ENTITY_STATES = [...salutationRoute, ...salutationPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalutationComponent,
        SalutationDetailComponent,
        SalutationUpdateComponent,
        SalutationDeleteDialogComponent,
        SalutationDeletePopupComponent
    ],
    entryComponents: [SalutationComponent, SalutationUpdateComponent, SalutationDeleteDialogComponent, SalutationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuSalutationModule {}
