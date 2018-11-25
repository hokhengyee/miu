import { IUser } from 'app/core/user/user.model';
import { ICourse } from 'app/shared/model/course.model';

export interface ICourseAccess {
    id?: number;
    user?: IUser;
    course?: ICourse;
}

export class CourseAccess implements ICourseAccess {
    constructor(public id?: number, public user?: IUser, public course?: ICourse) {}
}
