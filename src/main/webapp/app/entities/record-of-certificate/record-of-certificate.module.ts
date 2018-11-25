import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    RecordOfCertificateComponent,
    RecordOfCertificateDetailComponent,
    RecordOfCertificateUpdateComponent,
    RecordOfCertificateDeletePopupComponent,
    RecordOfCertificateDeleteDialogComponent,
    recordOfCertificateRoute,
    recordOfCertificatePopupRoute
} from './';

const ENTITY_STATES = [...recordOfCertificateRoute, ...recordOfCertificatePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecordOfCertificateComponent,
        RecordOfCertificateDetailComponent,
        RecordOfCertificateUpdateComponent,
        RecordOfCertificateDeleteDialogComponent,
        RecordOfCertificateDeletePopupComponent
    ],
    entryComponents: [
        RecordOfCertificateComponent,
        RecordOfCertificateUpdateComponent,
        RecordOfCertificateDeleteDialogComponent,
        RecordOfCertificateDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuRecordOfCertificateModule {}
