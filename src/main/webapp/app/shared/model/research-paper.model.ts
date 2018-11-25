import { ICourse } from 'app/shared/model/course.model';

export interface IResearchPaper {
    id?: number;
    code?: string;
    title?: string;
    showOrder?: number;
    description?: string;
    course?: ICourse;
}

export class ResearchPaper implements IResearchPaper {
    constructor(
        public id?: number,
        public code?: string,
        public title?: string,
        public showOrder?: number,
        public description?: string,
        public course?: ICourse
    ) {}
}
