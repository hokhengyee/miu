import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    CustomStudentReportTypeComponent,
    CustomStudentReportTypeDetailComponent,
    CustomStudentReportTypeUpdateComponent,
    CustomStudentReportTypeDeletePopupComponent,
    CustomStudentReportTypeDeleteDialogComponent,
    customStudentReportTypeRoute,
    customStudentReportTypePopupRoute
} from './';

const ENTITY_STATES = [...customStudentReportTypeRoute, ...customStudentReportTypePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CustomStudentReportTypeComponent,
        CustomStudentReportTypeDetailComponent,
        CustomStudentReportTypeUpdateComponent,
        CustomStudentReportTypeDeleteDialogComponent,
        CustomStudentReportTypeDeletePopupComponent
    ],
    entryComponents: [
        CustomStudentReportTypeComponent,
        CustomStudentReportTypeUpdateComponent,
        CustomStudentReportTypeDeleteDialogComponent,
        CustomStudentReportTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuCustomStudentReportTypeModule {}
