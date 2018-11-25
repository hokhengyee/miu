import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    ModuleTypeComponent,
    ModuleTypeDetailComponent,
    ModuleTypeUpdateComponent,
    ModuleTypeDeletePopupComponent,
    ModuleTypeDeleteDialogComponent,
    moduleTypeRoute,
    moduleTypePopupRoute
} from './';

const ENTITY_STATES = [...moduleTypeRoute, ...moduleTypePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ModuleTypeComponent,
        ModuleTypeDetailComponent,
        ModuleTypeUpdateComponent,
        ModuleTypeDeleteDialogComponent,
        ModuleTypeDeletePopupComponent
    ],
    entryComponents: [ModuleTypeComponent, ModuleTypeUpdateComponent, ModuleTypeDeleteDialogComponent, ModuleTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuModuleTypeModule {}
