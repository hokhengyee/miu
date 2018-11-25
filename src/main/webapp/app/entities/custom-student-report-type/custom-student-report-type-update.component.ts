import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';
import { CustomStudentReportTypeService } from './custom-student-report-type.service';

@Component({
    selector: 'jhi-custom-student-report-type-update',
    templateUrl: './custom-student-report-type-update.component.html'
})
export class CustomStudentReportTypeUpdateComponent implements OnInit {
    customStudentReportType: ICustomStudentReportType;
    isSaving: boolean;

    constructor(private customStudentReportTypeService: CustomStudentReportTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customStudentReportType }) => {
            this.customStudentReportType = customStudentReportType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customStudentReportType.id !== undefined) {
            this.subscribeToSaveResponse(this.customStudentReportTypeService.update(this.customStudentReportType));
        } else {
            this.subscribeToSaveResponse(this.customStudentReportTypeService.create(this.customStudentReportType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomStudentReportType>>) {
        result.subscribe(
            (res: HttpResponse<ICustomStudentReportType>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
