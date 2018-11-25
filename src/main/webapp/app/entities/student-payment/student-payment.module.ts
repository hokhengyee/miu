import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    StudentPaymentComponent,
    StudentPaymentDetailComponent,
    StudentPaymentUpdateComponent,
    StudentPaymentDeletePopupComponent,
    StudentPaymentDeleteDialogComponent,
    studentPaymentRoute,
    studentPaymentPopupRoute
} from './';

const ENTITY_STATES = [...studentPaymentRoute, ...studentPaymentPopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentPaymentComponent,
        StudentPaymentDetailComponent,
        StudentPaymentUpdateComponent,
        StudentPaymentDeleteDialogComponent,
        StudentPaymentDeletePopupComponent
    ],
    entryComponents: [
        StudentPaymentComponent,
        StudentPaymentUpdateComponent,
        StudentPaymentDeleteDialogComponent,
        StudentPaymentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStudentPaymentModule {}
