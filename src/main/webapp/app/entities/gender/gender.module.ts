import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    GenderComponent,
    GenderDetailComponent,
    GenderUpdateComponent,
    GenderDeletePopupComponent,
    GenderDeleteDialogComponent,
    genderRoute,
    genderPopupRoute
} from './';

const ENTITY_STATES = [...genderRoute, ...genderPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GenderComponent, GenderDetailComponent, GenderUpdateComponent, GenderDeleteDialogComponent, GenderDeletePopupComponent],
    entryComponents: [GenderComponent, GenderUpdateComponent, GenderDeleteDialogComponent, GenderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuGenderModule {}
