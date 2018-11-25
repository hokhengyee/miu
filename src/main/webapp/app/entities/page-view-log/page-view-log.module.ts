import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    PageViewLogComponent,
    PageViewLogDetailComponent,
    PageViewLogUpdateComponent,
    PageViewLogDeletePopupComponent,
    PageViewLogDeleteDialogComponent,
    pageViewLogRoute,
    pageViewLogPopupRoute
} from './';

const ENTITY_STATES = [...pageViewLogRoute, ...pageViewLogPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PageViewLogComponent,
        PageViewLogDetailComponent,
        PageViewLogUpdateComponent,
        PageViewLogDeleteDialogComponent,
        PageViewLogDeletePopupComponent
    ],
    entryComponents: [PageViewLogComponent, PageViewLogUpdateComponent, PageViewLogDeleteDialogComponent, PageViewLogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuPageViewLogModule {}
