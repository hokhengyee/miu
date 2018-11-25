import { Moment } from 'moment';

export interface IPageViewLog {
    id?: number;
    createdDate?: Moment;
    views?: number;
}

export class PageViewLog implements IPageViewLog {
    constructor(public id?: number, public createdDate?: Moment, public views?: number) {}
}
