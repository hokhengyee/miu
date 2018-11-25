import { IModuleType } from 'app/shared/model/module-type.model';
import { ICourse } from 'app/shared/model/course.model';

export interface IModule {
    id?: number;
    description?: string;
    moduleOrder?: number;
    moduleCode?: string;
    title?: string;
    moduleType?: IModuleType;
    course?: ICourse;
}

export class Module implements IModule {
    constructor(
        public id?: number,
        public description?: string,
        public moduleOrder?: number,
        public moduleCode?: string,
        public title?: string,
        public moduleType?: IModuleType,
        public course?: ICourse
    ) {}
}
