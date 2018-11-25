export interface IExternalOnlineResource {
    id?: number;
    title?: string;
    websiteLink?: string;
    description?: any;
}

export class ExternalOnlineResource implements IExternalOnlineResource {
    constructor(public id?: number, public title?: string, public websiteLink?: string, public description?: any) {}
}
