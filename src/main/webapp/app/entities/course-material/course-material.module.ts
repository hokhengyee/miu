import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    CourseMaterialComponent,
    CourseMaterialDetailComponent,
    CourseMaterialUpdateComponent,
    CourseMaterialDeletePopupComponent,
    CourseMaterialDeleteDialogComponent,
    courseMaterialRoute,
    courseMaterialPopupRoute
} from './';

const ENTITY_STATES = [...courseMaterialRoute, ...courseMaterialPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourseMaterialComponent,
        CourseMaterialDetailComponent,
        CourseMaterialUpdateComponent,
        CourseMaterialDeleteDialogComponent,
        CourseMaterialDeletePopupComponent
    ],
    entryComponents: [
        CourseMaterialComponent,
        CourseMaterialUpdateComponent,
        CourseMaterialDeleteDialogComponent,
        CourseMaterialDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuCourseMaterialModule {}
