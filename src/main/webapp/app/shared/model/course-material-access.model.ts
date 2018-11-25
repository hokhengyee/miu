import { ICourse } from 'app/shared/model/course.model';
import { ICourseMaterial } from 'app/shared/model/course-material.model';

export interface ICourseMaterialAccess {
    id?: number;
    displayOrder?: number;
    course?: ICourse;
    courseMaterial?: ICourseMaterial;
}

export class CourseMaterialAccess implements ICourseMaterialAccess {
    constructor(public id?: number, public displayOrder?: number, public course?: ICourse, public courseMaterial?: ICourseMaterial) {}
}
