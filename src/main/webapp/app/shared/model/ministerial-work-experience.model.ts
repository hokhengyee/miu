export interface IMinisterialWorkExperience {
    id?: number;
    nameOfMinistry2?: string;
    areaOfMinistry2?: string;
    nameOfMinistry3?: string;
    areaOfMinistry3?: string;
    nameOfMinistry4?: string;
    areaOfMinistry4?: string;
    md5Key?: string;
    years2?: number;
    years3?: number;
    years4?: number;
    nameOfMinistry1?: string;
    areaOfMinistry1?: string;
    years1?: number;
}

export class MinisterialWorkExperience implements IMinisterialWorkExperience {
    constructor(
        public id?: number,
        public nameOfMinistry2?: string,
        public areaOfMinistry2?: string,
        public nameOfMinistry3?: string,
        public areaOfMinistry3?: string,
        public nameOfMinistry4?: string,
        public areaOfMinistry4?: string,
        public md5Key?: string,
        public years2?: number,
        public years3?: number,
        public years4?: number,
        public nameOfMinistry1?: string,
        public areaOfMinistry1?: string,
        public years1?: number
    ) {}
}
