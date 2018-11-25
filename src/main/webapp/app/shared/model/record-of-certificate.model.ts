import { Moment } from 'moment';

export interface IRecordOfCertificate {
    id?: number;
    name?: string;
    degree?: string;
    studentNo?: string;
    certNumber?: string;
    certScanFileContentType?: string;
    certScanFile?: any;
    certDate?: Moment;
}

export class RecordOfCertificate implements IRecordOfCertificate {
    constructor(
        public id?: number,
        public name?: string,
        public degree?: string,
        public studentNo?: string,
        public certNumber?: string,
        public certScanFileContentType?: string,
        public certScanFile?: any,
        public certDate?: Moment
    ) {}
}
