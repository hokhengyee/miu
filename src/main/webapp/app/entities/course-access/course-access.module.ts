import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    CourseAccessComponent,
    CourseAccessDetailComponent,
    CourseAccessUpdateComponent,
    CourseAccessDeletePopupComponent,
    CourseAccessDeleteDialogComponent,
    courseAccessRoute,
    courseAccessPopupRoute
} from './';

const ENTITY_STATES = [...courseAccessRoute, ...courseAccessPopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourseAccessComponent,
        CourseAccessDetailComponent,
        CourseAccessUpdateComponent,
        CourseAccessDeleteDialogComponent,
        CourseAccessDeletePopupComponent
    ],
    entryComponents: [
        CourseAccessComponent,
        CourseAccessUpdateComponent,
        CourseAccessDeleteDialogComponent,
        CourseAccessDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuCourseAccessModule {}
