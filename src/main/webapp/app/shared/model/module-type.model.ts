export interface IModuleType {
    id?: number;
    title?: string;
    description?: string;
    moduleTypeOrder?: number;
}

export class ModuleType implements IModuleType {
    constructor(public id?: number, public title?: string, public description?: string, public moduleTypeOrder?: number) {}
}
