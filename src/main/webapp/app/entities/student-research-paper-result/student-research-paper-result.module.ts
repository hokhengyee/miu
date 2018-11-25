import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import { MiuAdminModule } from 'app/admin/admin.module';
import {
    StudentResearchPaperResultComponent,
    StudentResearchPaperResultDetailComponent,
    StudentResearchPaperResultUpdateComponent,
    StudentResearchPaperResultDeletePopupComponent,
    StudentResearchPaperResultDeleteDialogComponent,
    studentResearchPaperResultRoute,
    studentResearchPaperResultPopupRoute
} from './';

const ENTITY_STATES = [...studentResearchPaperResultRoute, ...studentResearchPaperResultPopupRoute];

@NgModule({
    imports: [MiuSharedModule, MiuAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentResearchPaperResultComponent,
        StudentResearchPaperResultDetailComponent,
        StudentResearchPaperResultUpdateComponent,
        StudentResearchPaperResultDeleteDialogComponent,
        StudentResearchPaperResultDeletePopupComponent
    ],
    entryComponents: [
        StudentResearchPaperResultComponent,
        StudentResearchPaperResultUpdateComponent,
        StudentResearchPaperResultDeleteDialogComponent,
        StudentResearchPaperResultDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuStudentResearchPaperResultModule {}
