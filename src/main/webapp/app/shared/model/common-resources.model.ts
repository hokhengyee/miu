export interface ICommonResources {
    id?: number;
    title?: string;
    contentContentType?: string;
    content?: any;
    description?: any;
    displayOrder?: number;
    websiteLink?: string;
}

export class CommonResources implements ICommonResources {
    constructor(
        public id?: number,
        public title?: string,
        public contentContentType?: string,
        public content?: any,
        public description?: any,
        public displayOrder?: number,
        public websiteLink?: string
    ) {}
}
