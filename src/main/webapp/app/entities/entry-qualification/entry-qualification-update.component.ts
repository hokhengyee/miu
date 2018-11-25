import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEntryQualification } from 'app/shared/model/entry-qualification.model';
import { EntryQualificationService } from './entry-qualification.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-entry-qualification-update',
    templateUrl: './entry-qualification-update.component.html'
})
export class EntryQualificationUpdateComponent implements OnInit {
    entryQualification: IEntryQualification;
    isSaving: boolean;

    courses: ICourse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private entryQualificationService: EntryQualificationService,
        private courseService: CourseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ entryQualification }) => {
            this.entryQualification = entryQualification;
        });
        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.entryQualification.id !== undefined) {
            this.subscribeToSaveResponse(this.entryQualificationService.update(this.entryQualification));
        } else {
            this.subscribeToSaveResponse(this.entryQualificationService.create(this.entryQualification));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEntryQualification>>) {
        result.subscribe((res: HttpResponse<IEntryQualification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
