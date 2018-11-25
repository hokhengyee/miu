import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    StudentProfileComponent,
    StudentProfileDetailComponent,
    StudentProfileUpdateComponent,
    StudentProfileDeletePopupComponent,
    StudentProfileDeleteDialogComponent,
    studentProfileRoute,
    studentProfilePopupRoute
} from './';

const ENTITY_STATES = [...studentProfileRoute, ...studentProfilePopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentProfileComponent,
        StudentProfileDetailComponent,
        StudentProfileUpdateComponent,
        StudentProfileDeleteDialogComponent,
        StudentProfileDeletePopupComponent
    ],
    entryComponents: [
        StudentProfileComponent,
        StudentProfileUpdateComponent,
        StudentProfileDeleteDialogComponent,
        StudentProfileDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStudentProfileModule {}
