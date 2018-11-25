import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    CourseModuleComponent,
    CourseModuleDetailComponent,
    CourseModuleUpdateComponent,
    CourseModuleDeletePopupComponent,
    CourseModuleDeleteDialogComponent,
    courseModuleRoute,
    courseModulePopupRoute
} from './';

const ENTITY_STATES = [...courseModuleRoute, ...courseModulePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourseModuleComponent,
        CourseModuleDetailComponent,
        CourseModuleUpdateComponent,
        CourseModuleDeleteDialogComponent,
        CourseModuleDeletePopupComponent
    ],
    entryComponents: [
        CourseModuleComponent,
        CourseModuleUpdateComponent,
        CourseModuleDeleteDialogComponent,
        CourseModuleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuCourseModuleModule {}
