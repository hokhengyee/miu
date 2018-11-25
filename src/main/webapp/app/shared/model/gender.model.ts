export interface IGender {
    id?: number;
    title?: string;
}

export class Gender implements IGender {
    constructor(public id?: number, public title?: string) {}
}
