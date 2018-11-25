import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStudentPayment } from 'app/shared/model/student-payment.model';

@Component({
    selector: 'jhi-student-payment-detail',
    templateUrl: './student-payment-detail.component.html'
})
export class StudentPaymentDetailComponent implements OnInit {
    studentPayment: IStudentPayment;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentPayment }) => {
            this.studentPayment = studentPayment;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
