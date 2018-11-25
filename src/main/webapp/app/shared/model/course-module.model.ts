import { ICourse } from 'app/shared/model/course.model';
import { IModule } from 'app/shared/model/module.model';

export interface ICourseModule {
    id?: number;
    displayOrder?: number;
    course?: ICourse;
    module?: IModule;
}

export class CourseModule implements ICourseModule {
    constructor(public id?: number, public displayOrder?: number, public course?: ICourse, public module?: IModule) {}
}
