import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    PaymentTypeComponent,
    PaymentTypeDetailComponent,
    PaymentTypeUpdateComponent,
    PaymentTypeDeletePopupComponent,
    PaymentTypeDeleteDialogComponent,
    paymentTypeRoute,
    paymentTypePopupRoute
} from './';

const ENTITY_STATES = [...paymentTypeRoute, ...paymentTypePopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentTypeComponent,
        PaymentTypeDetailComponent,
        PaymentTypeUpdateComponent,
        PaymentTypeDeleteDialogComponent,
        PaymentTypeDeletePopupComponent
    ],
    entryComponents: [PaymentTypeComponent, PaymentTypeUpdateComponent, PaymentTypeDeleteDialogComponent, PaymentTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuPaymentTypeModule {}
