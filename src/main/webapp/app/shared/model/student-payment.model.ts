import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICourse } from 'app/shared/model/course.model';
import { IPaymentType } from 'app/shared/model/payment-type.model';

export interface IStudentPayment {
    id?: number;
    createdDate?: Moment;
    amount?: string;
    description?: any;
    paymentDate?: Moment;
    paid?: boolean;
    user?: IUser;
    course?: ICourse;
    paymentType?: IPaymentType;
}

export class StudentPayment implements IStudentPayment {
    constructor(
        public id?: number,
        public createdDate?: Moment,
        public amount?: string,
        public description?: any,
        public paymentDate?: Moment,
        public paid?: boolean,
        public user?: IUser,
        public course?: ICourse,
        public paymentType?: IPaymentType
    ) {
        this.paid = this.paid || false;
    }
}
