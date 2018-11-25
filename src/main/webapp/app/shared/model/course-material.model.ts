import { ICourse } from 'app/shared/model/course.model';

export interface ICourseMaterial {
    id?: number;
    title?: string;
    description?: any;
    websiteLink?: string;
    contentContentType?: string;
    content?: any;
    displayOrder?: number;
    course?: ICourse;
}

export class CourseMaterial implements ICourseMaterial {
    constructor(
        public id?: number,
        public title?: string,
        public description?: any,
        public websiteLink?: string,
        public contentContentType?: string,
        public content?: any,
        public displayOrder?: number,
        public course?: ICourse
    ) {}
}
