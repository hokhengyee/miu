import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourseAccess } from 'app/shared/model/course-access.model';
import { CourseAccessService } from './course-access.service';
import { IUser, UserService } from 'app/core';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-course-access-update',
    templateUrl: './course-access-update.component.html'
})
export class CourseAccessUpdateComponent implements OnInit {
    courseAccess: ICourseAccess;
    isSaving: boolean;

    users: IUser[];

    courses: ICourse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private courseAccessService: CourseAccessService,
        private userService: UserService,
        private courseService: CourseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courseAccess }) => {
            this.courseAccess = courseAccess;
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courseAccess.id !== undefined) {
            this.subscribeToSaveResponse(this.courseAccessService.update(this.courseAccess));
        } else {
            this.subscribeToSaveResponse(this.courseAccessService.create(this.courseAccess));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourseAccess>>) {
        result.subscribe((res: HttpResponse<ICourseAccess>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
