import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';

@Component({
    selector: 'jhi-custom-student-report-type-detail',
    templateUrl: './custom-student-report-type-detail.component.html'
})
export class CustomStudentReportTypeDetailComponent implements OnInit {
    customStudentReportType: ICustomStudentReportType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ customStudentReportType }) => {
            this.customStudentReportType = customStudentReportType;
        });
    }

    previousState() {
        window.history.back();
    }
}
