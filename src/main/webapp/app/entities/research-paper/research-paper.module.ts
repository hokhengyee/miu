import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    ResearchPaperComponent,
    ResearchPaperDetailComponent,
    ResearchPaperUpdateComponent,
    ResearchPaperDeletePopupComponent,
    ResearchPaperDeleteDialogComponent,
    researchPaperRoute,
    researchPaperPopupRoute
} from './';

const ENTITY_STATES = [...researchPaperRoute, ...researchPaperPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResearchPaperComponent,
        ResearchPaperDetailComponent,
        ResearchPaperUpdateComponent,
        ResearchPaperDeleteDialogComponent,
        ResearchPaperDeletePopupComponent
    ],
    entryComponents: [
        ResearchPaperComponent,
        ResearchPaperUpdateComponent,
        ResearchPaperDeleteDialogComponent,
        ResearchPaperDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuResearchPaperModule {}
