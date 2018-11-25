import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    StudentOtherResultComponent,
    StudentOtherResultDetailComponent,
    StudentOtherResultUpdateComponent,
    StudentOtherResultDeletePopupComponent,
    StudentOtherResultDeleteDialogComponent,
    studentOtherResultRoute,
    studentOtherResultPopupRoute
} from './';

const ENTITY_STATES = [...studentOtherResultRoute, ...studentOtherResultPopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentOtherResultComponent,
        StudentOtherResultDetailComponent,
        StudentOtherResultUpdateComponent,
        StudentOtherResultDeleteDialogComponent,
        StudentOtherResultDeletePopupComponent
    ],
    entryComponents: [
        StudentOtherResultComponent,
        StudentOtherResultUpdateComponent,
        StudentOtherResultDeleteDialogComponent,
        StudentOtherResultDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStudentOtherResultModule {}
