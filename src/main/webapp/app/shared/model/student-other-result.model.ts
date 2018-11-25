import { Moment } from 'moment';
import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';
import { IUser } from 'app/core/user/user.model';

export interface IStudentOtherResult {
    id?: number;
    code?: string;
    title?: string;
    result?: string;
    dateGraded?: Moment;
    resultOrder?: number;
    customStudentReportType?: ICustomStudentReportType;
    user?: IUser;
}

export class StudentOtherResult implements IStudentOtherResult {
    constructor(
        public id?: number,
        public code?: string,
        public title?: string,
        public result?: string,
        public dateGraded?: Moment,
        public resultOrder?: number,
        public customStudentReportType?: ICustomStudentReportType,
        public user?: IUser
    ) {}
}
