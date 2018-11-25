export interface IAcademicCertificate {
    id?: number;
    md5Key?: string;
    academicCertificate1ContentType?: string;
    academicCertificate1?: any;
    academicCertificate2ContentType?: string;
    academicCertificate2?: any;
    academicCertificate3ContentType?: string;
    academicCertificate3?: any;
}

export class AcademicCertificate implements IAcademicCertificate {
    constructor(
        public id?: number,
        public md5Key?: string,
        public academicCertificate1ContentType?: string,
        public academicCertificate1?: any,
        public academicCertificate2ContentType?: string,
        public academicCertificate2?: any,
        public academicCertificate3ContentType?: string,
        public academicCertificate3?: any
    ) {}
}
