import { IUser } from 'app/core/user/user.model';
import { ISalutation } from 'app/shared/model/salutation.model';

export interface ILecturerProfile {
    id?: number;
    otherTitles?: string;
    age?: number;
    ordination?: string;
    academicHistory?: any;
    professionalHistory?: any;
    pastAndCurrentMinistry?: any;
    publications?: any;
    familyDetails?: any;
    reference?: any;
    profilePhotoContentType?: string;
    profilePhoto?: any;
    user?: IUser;
    salutation?: ISalutation;
}

export class LecturerProfile implements ILecturerProfile {
    constructor(
        public id?: number,
        public otherTitles?: string,
        public age?: number,
        public ordination?: string,
        public academicHistory?: any,
        public professionalHistory?: any,
        public pastAndCurrentMinistry?: any,
        public publications?: any,
        public familyDetails?: any,
        public reference?: any,
        public profilePhotoContentType?: string,
        public profilePhoto?: any,
        public user?: IUser,
        public salutation?: ISalutation
    ) {}
}
