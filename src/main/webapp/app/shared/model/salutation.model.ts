export interface ISalutation {
    id?: number;
    title?: string;
    displayOrder?: number;
}

export class Salutation implements ISalutation {
    constructor(public id?: number, public title?: string, public displayOrder?: number) {}
}
