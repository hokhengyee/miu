import { Moment } from 'moment';
import { ISalutation } from 'app/shared/model/salutation.model';
import { IGender } from 'app/shared/model/gender.model';
import { IUser } from 'app/core/user/user.model';

export interface IStudentProfile {
    id?: number;
    studentId?: string;
    dateOfBirth?: Moment;
    phone?: string;
    applicationDate?: Moment;
    commencementDate?: Moment;
    completionDate?: Moment;
    mailingAddress?: any;
    profilePhotoContentType?: string;
    profilePhoto?: any;
    extendedCompletionDate?: Moment;
    salutation?: ISalutation;
    gender?: IGender;
    supervisor?: IUser;
    user?: IUser;
}

export class StudentProfile implements IStudentProfile {
    constructor(
        public id?: number,
        public studentId?: string,
        public dateOfBirth?: Moment,
        public phone?: string,
        public applicationDate?: Moment,
        public commencementDate?: Moment,
        public completionDate?: Moment,
        public mailingAddress?: any,
        public profilePhotoContentType?: string,
        public profilePhoto?: any,
        public extendedCompletionDate?: Moment,
        public salutation?: ISalutation,
        public gender?: IGender,
        public supervisor?: IUser,
        public user?: IUser
    ) {}
}
