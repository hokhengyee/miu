import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    StudentModuleResultComponent,
    StudentModuleResultDetailComponent,
    StudentModuleResultUpdateComponent,
    StudentModuleResultDeletePopupComponent,
    StudentModuleResultDeleteDialogComponent,
    studentModuleResultRoute,
    studentModuleResultPopupRoute
} from './';

const ENTITY_STATES = [...studentModuleResultRoute, ...studentModuleResultPopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentModuleResultComponent,
        StudentModuleResultDetailComponent,
        StudentModuleResultUpdateComponent,
        StudentModuleResultDeleteDialogComponent,
        StudentModuleResultDeletePopupComponent
    ],
    entryComponents: [
        StudentModuleResultComponent,
        StudentModuleResultUpdateComponent,
        StudentModuleResultDeleteDialogComponent,
        StudentModuleResultDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStudentModuleResultModule {}
