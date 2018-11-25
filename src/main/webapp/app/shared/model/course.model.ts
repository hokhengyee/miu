export interface ICourse {
    id?: number;
    description?: string;
    courseOrder?: number;
    title?: string;
    creditHours?: number;
    applicationFee?: string;
    registrationFee?: string;
    courseFee?: string;
}

export class Course implements ICourse {
    constructor(
        public id?: number,
        public description?: string,
        public courseOrder?: number,
        public title?: string,
        public creditHours?: number,
        public applicationFee?: string,
        public registrationFee?: string,
        public courseFee?: string
    ) {}
}
