import { ICourse } from 'app/shared/model/course.model';

export interface IEntryQualification {
    id?: number;
    content?: string;
    displayOrder?: number;
    course?: ICourse;
}

export class EntryQualification implements IEntryQualification {
    constructor(public id?: number, public content?: string, public displayOrder?: number, public course?: ICourse) {}
}
