import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';

export interface IAdjunctFaculty {
    id?: number;
    showOrder?: number;
    lecturerProfile?: ILecturerProfile;
}

export class AdjunctFaculty implements IAdjunctFaculty {
    constructor(public id?: number, public showOrder?: number, public lecturerProfile?: ILecturerProfile) {}
}
