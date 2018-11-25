export interface IStaticPageType {
    id?: number;
    title?: string;
}

export class StaticPageType implements IStaticPageType {
    constructor(public id?: number, public title?: string) {}
}
