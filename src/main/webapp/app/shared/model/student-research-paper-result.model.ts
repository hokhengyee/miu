import { Moment } from 'moment';
import { IResearchPaper } from 'app/shared/model/research-paper.model';
import { IUser } from 'app/core/user/user.model';

export interface IStudentResearchPaperResult {
    id?: number;
    result?: string;
    dateGraded?: Moment;
    resultOrder?: number;
    researchPaper?: IResearchPaper;
    user?: IUser;
}

export class StudentResearchPaperResult implements IStudentResearchPaperResult {
    constructor(
        public id?: number,
        public result?: string,
        public dateGraded?: Moment,
        public resultOrder?: number,
        public researchPaper?: IResearchPaper,
        public user?: IUser
    ) {}
}
