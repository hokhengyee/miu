import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IModule } from 'app/shared/model/module.model';

export interface IStudentModuleResult {
    id?: number;
    result?: string;
    dateGraded?: Moment;
    resultOrder?: number;
    user?: IUser;
    module?: IModule;
}

export class StudentModuleResult implements IStudentModuleResult {
    constructor(
        public id?: number,
        public result?: string,
        public dateGraded?: Moment,
        public resultOrder?: number,
        public user?: IUser,
        public module?: IModule
    ) {}
}
