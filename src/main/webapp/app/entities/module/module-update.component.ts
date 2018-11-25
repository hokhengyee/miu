import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';
import { IModuleType } from 'app/shared/model/module-type.model';
import { ModuleTypeService } from 'app/entities/module-type';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-module-update',
    templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
    module: IModule;
    isSaving: boolean;

    moduletypes: IModuleType[];

    courses: ICourse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private moduleService: ModuleService,
        private moduleTypeService: ModuleTypeService,
        private courseService: CourseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ module }) => {
            this.module = module;
        });
        this.moduleTypeService.query().subscribe(
            (res: HttpResponse<IModuleType[]>) => {
                this.moduletypes = res.body;
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
        if (this.module.id !== undefined) {
            this.subscribeToSaveResponse(this.moduleService.update(this.module));
        } else {
            this.subscribeToSaveResponse(this.moduleService.create(this.module));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>) {
        result.subscribe((res: HttpResponse<IModule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackModuleTypeById(index: number, item: IModuleType) {
        return item.id;
    }

    trackCourseById(index: number, item: ICourse) {
        return item.id;
    }
}
