import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICourseMaterial } from 'app/shared/model/course-material.model';
import { CourseMaterialService } from './course-material.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-course-material-update',
    templateUrl: './course-material-update.component.html'
})
export class CourseMaterialUpdateComponent implements OnInit {
    courseMaterial: ICourseMaterial;
    isSaving: boolean;

    courses: ICourse[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private courseMaterialService: CourseMaterialService,
        private courseService: CourseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courseMaterial }) => {
            this.courseMaterial = courseMaterial;
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courseMaterial.id !== undefined) {
            this.subscribeToSaveResponse(this.courseMaterialService.update(this.courseMaterial));
        } else {
            this.subscribeToSaveResponse(this.courseMaterialService.create(this.courseMaterial));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourseMaterial>>) {
        result.subscribe((res: HttpResponse<ICourseMaterial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
