import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IOnlineApplication } from 'app/shared/model/online-application.model';
import { OnlineApplicationService } from './online-application.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-online-application-update',
    templateUrl: './online-application-update.component.html'
})
export class OnlineApplicationUpdateComponent implements OnInit {
    onlineApplication: IOnlineApplication;
    isSaving: boolean;

    courses: ICourse[];
    dateOfBirthDp: any;
    registrationDatetime: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private onlineApplicationService: OnlineApplicationService,
        private courseService: CourseService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ onlineApplication }) => {
            this.onlineApplication = onlineApplication;
            this.registrationDatetime =
                this.onlineApplication.registrationDatetime != null
                    ? this.onlineApplication.registrationDatetime.format(DATE_TIME_FORMAT)
                    : null;
        });
        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.courses = res.body;
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.onlineApplication, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.onlineApplication.registrationDatetime =
            this.registrationDatetime != null ? moment(this.registrationDatetime, DATE_TIME_FORMAT) : null;
        if (this.onlineApplication.id !== undefined) {
            this.subscribeToSaveResponse(this.onlineApplicationService.update(this.onlineApplication));
        } else {
            this.subscribeToSaveResponse(this.onlineApplicationService.create(this.onlineApplication));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOnlineApplication>>) {
        result.subscribe((res: HttpResponse<IOnlineApplication>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCourseById(index: number, item: ICourse) {
        return item.id;
    }
}
