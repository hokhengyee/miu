export interface IPaymentType {
    id?: number;
    title?: string;
}

export class PaymentType implements IPaymentType {
    constructor(public id?: number, public title?: string) {}
}
