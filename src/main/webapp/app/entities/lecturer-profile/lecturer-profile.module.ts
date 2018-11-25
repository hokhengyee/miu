import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    LecturerProfileComponent,
    LecturerProfileDetailComponent,
    LecturerProfileUpdateComponent,
    LecturerProfileDeletePopupComponent,
    LecturerProfileDeleteDialogComponent,
    lecturerProfileRoute,
    lecturerProfilePopupRoute
} from './';

const ENTITY_STATES = [...lecturerProfileRoute, ...lecturerProfilePopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LecturerProfileComponent,
        LecturerProfileDetailComponent,
        LecturerProfileUpdateComponent,
        LecturerProfileDeleteDialogComponent,
        LecturerProfileDeletePopupComponent
    ],
    entryComponents: [
        LecturerProfileComponent,
        LecturerProfileUpdateComponent,
        LecturerProfileDeleteDialogComponent,
        LecturerProfileDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuLecturerProfileModule {}
