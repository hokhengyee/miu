export interface ICustomStudentReportType {
    id?: number;
    label?: string;
}

export class CustomStudentReportType implements ICustomStudentReportType {
    constructor(public id?: number, public label?: string) {}
}
