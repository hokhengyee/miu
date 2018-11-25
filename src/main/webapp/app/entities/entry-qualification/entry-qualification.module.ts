import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    EntryQualificationComponent,
    EntryQualificationDetailComponent,
    EntryQualificationUpdateComponent,
    EntryQualificationDeletePopupComponent,
    EntryQualificationDeleteDialogComponent,
    entryQualificationRoute,
    entryQualificationPopupRoute
} from './';

const ENTITY_STATES = [...entryQualificationRoute, ...entryQualificationPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EntryQualificationComponent,
        EntryQualificationDetailComponent,
        EntryQualificationUpdateComponent,
        EntryQualificationDeleteDialogComponent,
        EntryQualificationDeletePopupComponent
    ],
    entryComponents: [
        EntryQualificationComponent,
        EntryQualificationUpdateComponent,
        EntryQualificationDeleteDialogComponent,
        EntryQualificationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuEntryQualificationModule {}
