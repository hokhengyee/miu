import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IStudentOtherResult } from 'app/shared/model/student-other-result.model';
import { StudentOtherResultService } from './student-other-result.service';
import { ICustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';
import { CustomStudentReportTypeService } from 'app/entities/custom-student-report-type';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-student-other-result-update',
    templateUrl: './student-other-result-update.component.html'
})
export class StudentOtherResultUpdateComponent implements OnInit {
    studentOtherResult: IStudentOtherResult;
    isSaving: boolean;

    customstudentreporttypes: ICustomStudentReportType[];

    users: IUser[];
    dateGradedDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private studentOtherResultService: StudentOtherResultService,
        private customStudentReportTypeService: CustomStudentReportTypeService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentOtherResult }) => {
            this.studentOtherResult = studentOtherResult;
        });
        this.customStudentReportTypeService.query().subscribe(
            (res: HttpResponse<ICustomStudentReportType[]>) => {
                this.customstudentreporttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentOtherResult.id !== undefined) {
            this.subscribeToSaveResponse(this.studentOtherResultService.update(this.studentOtherResult));
        } else {
            this.subscribeToSaveResponse(this.studentOtherResultService.create(this.studentOtherResult));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudentOtherResult>>) {
        result.subscribe((res: HttpResponse<IStudentOtherResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomStudentReportTypeById(index: number, item: ICustomStudentReportType) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
