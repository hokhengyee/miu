export interface IRegistrationAcademicDetails {
    id?: number;
    nameOfInstitution2?: string;
    examPassed2?: string;
    year2?: number;
    grade2?: string;
    nameOfInstitution3?: string;
    examPassed3?: string;
    year3?: number;
    grade3?: string;
    nameOfInstitution4?: string;
    examPassed4?: string;
    year4?: number;
    grade4?: string;
    md5key?: string;
    nameOfInstitution1?: string;
    examPassed1?: string;
    year1?: number;
    grade1?: string;
}

export class RegistrationAcademicDetails implements IRegistrationAcademicDetails {
    constructor(
        public id?: number,
        public nameOfInstitution2?: string,
        public examPassed2?: string,
        public year2?: number,
        public grade2?: string,
        public nameOfInstitution3?: string,
        public examPassed3?: string,
        public year3?: number,
        public grade3?: string,
        public nameOfInstitution4?: string,
        public examPassed4?: string,
        public year4?: number,
        public grade4?: string,
        public md5key?: string,
        public nameOfInstitution1?: string,
        public examPassed1?: string,
        public year1?: number,
        public grade1?: string
    ) {}
}
