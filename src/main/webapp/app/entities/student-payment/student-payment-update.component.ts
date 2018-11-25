import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IStudentPayment } from 'app/shared/model/student-payment.model';
import { StudentPaymentService } from './student-payment.service';
import { IUser, UserService } from 'app/core';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from 'app/entities/payment-type';

@Component({
    selector: 'jhi-student-payment-update',
    templateUrl: './student-payment-update.component.html'
})
export class StudentPaymentUpdateComponent implements OnInit {
    studentPayment: IStudentPayment;
    isSaving: boolean;

    users: IUser[];

    courses: ICourse[];

    paymenttypes: IPaymentType[];
    createdDate: string;
    paymentDateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private studentPaymentService: StudentPaymentService,
        private userService: UserService,
        private courseService: CourseService,
        private paymentTypeService: PaymentTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentPayment }) => {
            this.studentPayment = studentPayment;
            this.createdDate = this.studentPayment.createdDate != null ? this.studentPayment.createdDate.format(DATE_TIME_FORMAT) : null;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentTypeService.query().subscribe(
            (res: HttpResponse<IPaymentType[]>) => {
                this.paymenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.studentPayment.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        if (this.studentPayment.id !== undefined) {
            this.subscribeToSaveResponse(this.studentPaymentService.update(this.studentPayment));
        } else {
            this.subscribeToSaveResponse(this.studentPaymentService.create(this.studentPayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudentPayment>>) {
        result.subscribe((res: HttpResponse<IStudentPayment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackCourseById(index: number, item: ICourse) {
        return item.id;
    }

    trackPaymentTypeById(index: number, item: IPaymentType) {
        return item.id;
    }
}
