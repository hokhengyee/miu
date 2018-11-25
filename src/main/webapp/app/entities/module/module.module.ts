import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    ModuleComponent,
    ModuleDetailComponent,
    ModuleUpdateComponent,
    ModuleDeletePopupComponent,
    ModuleDeleteDialogComponent,
    moduleRoute,
    modulePopupRoute
} from './';

const ENTITY_STATES = [...moduleRoute, ...modulePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ModuleComponent, ModuleDetailComponent, ModuleUpdateComponent, ModuleDeleteDialogComponent, ModuleDeletePopupComponent],
    entryComponents: [ModuleComponent, ModuleUpdateComponent, ModuleDeleteDialogComponent, ModuleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuModuleModule {}
