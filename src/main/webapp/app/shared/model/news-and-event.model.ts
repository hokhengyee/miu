import { Moment } from 'moment';

export interface INewsAndEvent {
    id?: number;
    title?: string;
    websiteLink?: string;
    startDT?: Moment;
    endDT?: Moment;
    venue?: string;
    eventDetail?: any;
}

export class NewsAndEvent implements INewsAndEvent {
    constructor(
        public id?: number,
        public title?: string,
        public websiteLink?: string,
        public startDT?: Moment,
        public endDT?: Moment,
        public venue?: string,
        public eventDetail?: any
    ) {}
}
