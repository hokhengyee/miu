import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourseMaterialAccess } from 'app/shared/model/course-material-access.model';
import { CourseMaterialAccessService } from './course-material-access.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';
import { ICourseMaterial } from 'app/shared/model/course-material.model';
import { CourseMaterialService } from 'app/entities/course-material';

@Component({
    selector: 'jhi-course-material-access-update',
    templateUrl: './course-material-access-update.component.html'
})
export class CourseMaterialAccessUpdateComponent implements OnInit {
    courseMaterialAccess: ICourseMaterialAccess;
    isSaving: boolean;

    courses: ICourse[];

    coursematerials: ICourseMaterial[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private courseMaterialAccessService: CourseMaterialAccessService,
        private courseService: CourseService,
        private courseMaterialService: CourseMaterialService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courseMaterialAccess }) => {
            this.courseMaterialAccess = courseMaterialAccess;
        });
        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.courseMaterialService.query().subscribe(
            (res: HttpResponse<ICourseMaterial[]>) => {
                this.coursematerials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courseMaterialAccess.id !== undefined) {
            this.subscribeToSaveResponse(this.courseMaterialAccessService.update(this.courseMaterialAccess));
        } else {
            this.subscribeToSaveResponse(this.courseMaterialAccessService.create(this.courseMaterialAccess));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourseMaterialAccess>>) {
        result.subscribe(
            (res: HttpResponse<ICourseMaterialAccess>) => this.onSaveSuccess(),
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCourseById(index: number, item: ICourse) {
        return item.id;
    }

    trackCourseMaterialById(index: number, item: ICourseMaterial) {
        return item.id;
    }
}
