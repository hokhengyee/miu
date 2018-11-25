import { Moment } from 'moment';
import { ICourse } from 'app/shared/model/course.model';

export interface IOnlineApplication {
    id?: number;
    dateOfBirth?: Moment;
    telephone?: string;
    email?: string;
    city?: string;
    state?: string;
    country?: string;
    postcode?: string;
    registrationDatetime?: Moment;
    surname?: string;
    givenName?: string;
    address?: string;
    profilePhotoContentType?: string;
    profilePhoto?: any;
    academicCertificateContentType?: string;
    academicCertificate?: any;
    letterOfRecommendationContentType?: string;
    letterOfRecommendation?: any;
    profileDocumentContentType?: string;
    profileDocument?: any;
    md5key?: string;
    course?: ICourse;
}

export class OnlineApplication implements IOnlineApplication {
    constructor(
        public id?: number,
        public dateOfBirth?: Moment,
        public telephone?: string,
        public email?: string,
        public city?: string,
        public state?: string,
        public country?: string,
        public postcode?: string,
        public registrationDatetime?: Moment,
        public surname?: string,
        public givenName?: string,
        public address?: string,
        public profilePhotoContentType?: string,
        public profilePhoto?: any,
        public academicCertificateContentType?: string,
        public academicCertificate?: any,
        public letterOfRecommendationContentType?: string,
        public letterOfRecommendation?: any,
        public profileDocumentContentType?: string,
        public profileDocument?: any,
        public md5key?: string,
        public course?: ICourse
    ) {}
}
