import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    AdjunctFacultyComponent,
    AdjunctFacultyDetailComponent,
    AdjunctFacultyUpdateComponent,
    AdjunctFacultyDeletePopupComponent,
    AdjunctFacultyDeleteDialogComponent,
    adjunctFacultyRoute,
    adjunctFacultyPopupRoute
} from './';

const ENTITY_STATES = [...adjunctFacultyRoute, ...adjunctFacultyPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdjunctFacultyComponent,
        AdjunctFacultyDetailComponent,
        AdjunctFacultyUpdateComponent,
        AdjunctFacultyDeleteDialogComponent,
        AdjunctFacultyDeletePopupComponent
    ],
    entryComponents: [
        AdjunctFacultyComponent,
        AdjunctFacultyUpdateComponent,
        AdjunctFacultyDeleteDialogComponent,
        AdjunctFacultyDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuAdjunctFacultyModule {}
