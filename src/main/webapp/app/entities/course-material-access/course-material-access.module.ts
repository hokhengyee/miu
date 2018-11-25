import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    CourseMaterialAccessComponent,
    CourseMaterialAccessDetailComponent,
    CourseMaterialAccessUpdateComponent,
    CourseMaterialAccessDeletePopupComponent,
    CourseMaterialAccessDeleteDialogComponent,
    courseMaterialAccessRoute,
    courseMaterialAccessPopupRoute
} from './';

const ENTITY_STATES = [...courseMaterialAccessRoute, ...courseMaterialAccessPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourseMaterialAccessComponent,
        CourseMaterialAccessDetailComponent,
        CourseMaterialAccessUpdateComponent,
        CourseMaterialAccessDeleteDialogComponent,
        CourseMaterialAccessDeletePopupComponent
    ],
    entryComponents: [
        CourseMaterialAccessComponent,
        CourseMaterialAccessUpdateComponent,
        CourseMaterialAccessDeleteDialogComponent,
        CourseMaterialAccessDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuCourseMaterialAccessModule {}
