import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    MinisterialWorkExperienceComponent,
    MinisterialWorkExperienceDetailComponent,
    MinisterialWorkExperienceUpdateComponent,
    MinisterialWorkExperienceDeletePopupComponent,
    MinisterialWorkExperienceDeleteDialogComponent,
    ministerialWorkExperienceRoute,
    ministerialWorkExperiencePopupRoute
} from './';

const ENTITY_STATES = [...ministerialWorkExperienceRoute, ...ministerialWorkExperiencePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MinisterialWorkExperienceComponent,
        MinisterialWorkExperienceDetailComponent,
        MinisterialWorkExperienceUpdateComponent,
        MinisterialWorkExperienceDeleteDialogComponent,
        MinisterialWorkExperienceDeletePopupComponent
    ],
    entryComponents: [
        MinisterialWorkExperienceComponent,
        MinisterialWorkExperienceUpdateComponent,
        MinisterialWorkExperienceDeleteDialogComponent,
        MinisterialWorkExperienceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuMinisterialWorkExperienceModule {}
