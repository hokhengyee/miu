import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    RegistrationAcademicDetailsComponent,
    RegistrationAcademicDetailsDetailComponent,
    RegistrationAcademicDetailsUpdateComponent,
    RegistrationAcademicDetailsDeletePopupComponent,
    RegistrationAcademicDetailsDeleteDialogComponent,
    registrationAcademicDetailsRoute,
    registrationAcademicDetailsPopupRoute
} from './';

const ENTITY_STATES = [...registrationAcademicDetailsRoute, ...registrationAcademicDetailsPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegistrationAcademicDetailsComponent,
        RegistrationAcademicDetailsDetailComponent,
        RegistrationAcademicDetailsUpdateComponent,
        RegistrationAcademicDetailsDeleteDialogComponent,
        RegistrationAcademicDetailsDeletePopupComponent
    ],
    entryComponents: [
        RegistrationAcademicDetailsComponent,
        RegistrationAcademicDetailsUpdateComponent,
        RegistrationAcademicDetailsDeleteDialogComponent,
        RegistrationAcademicDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuRegistrationAcademicDetailsModule {}
