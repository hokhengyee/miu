import { IStaticPageType } from 'app/shared/model/static-page-type.model';

export interface IStaticPage {
    id?: number;
    content?: string;
    staticPageType?: IStaticPageType;
}

export class StaticPage implements IStaticPage {
    constructor(public id?: number, public content?: string, public staticPageType?: IStaticPageType) {}
}
