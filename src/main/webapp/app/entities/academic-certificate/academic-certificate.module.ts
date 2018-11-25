import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    AcademicCertificateComponent,
    AcademicCertificateDetailComponent,
    AcademicCertificateUpdateComponent,
    AcademicCertificateDeletePopupComponent,
    AcademicCertificateDeleteDialogComponent,
    academicCertificateRoute,
    academicCertificatePopupRoute
} from './';

const ENTITY_STATES = [...academicCertificateRoute, ...academicCertificatePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AcademicCertificateComponent,
        AcademicCertificateDetailComponent,
        AcademicCertificateUpdateComponent,
        AcademicCertificateDeleteDialogComponent,
        AcademicCertificateDeletePopupComponent
    ],
    entryComponents: [
        AcademicCertificateComponent,
        AcademicCertificateUpdateComponent,
        AcademicCertificateDeleteDialogComponent,
        AcademicCertificateDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuAcademicCertificateModule {}
