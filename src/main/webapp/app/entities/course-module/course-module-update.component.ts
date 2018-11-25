import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourseModule } from 'app/shared/model/course-module.model';
import { CourseModuleService } from './course-module.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-course-module-update',
    templateUrl: './course-module-update.component.html'
})
export class CourseModuleUpdateComponent implements OnInit {
    courseModule: ICourseModule;
    isSaving: boolean;

    courses: ICourse[];

    modules: IModule[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private courseModuleService: CourseModuleService,
        private courseService: CourseService,
        private moduleService: ModuleService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courseModule }) => {
            this.courseModule = courseModule;
        });
        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.moduleService.query().subscribe(
            (res: HttpResponse<IModule[]>) => {
                this.modules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courseModule.id !== undefined) {
            this.subscribeToSaveResponse(this.courseModuleService.update(this.courseModule));
        } else {
            this.subscribeToSaveResponse(this.courseModuleService.create(this.courseModule));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourseModule>>) {
        result.subscribe((res: HttpResponse<ICourseModule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackModuleById(index: number, item: IModule) {
        return item.id;
    }
}
