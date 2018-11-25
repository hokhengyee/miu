import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IStudentModuleResult } from 'app/shared/model/student-module-result.model';
import { StudentModuleResultService } from './student-module-result.service';
import { IUser, UserService } from 'app/core';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-student-module-result-update',
    templateUrl: './student-module-result-update.component.html'
})
export class StudentModuleResultUpdateComponent implements OnInit {
    studentModuleResult: IStudentModuleResult;
    isSaving: boolean;

    users: IUser[];

    modules: IModule[];
    dateGradedDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private studentModuleResultService: StudentModuleResultService,
        private userService: UserService,
        private moduleService: ModuleService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentModuleResult }) => {
            this.studentModuleResult = studentModuleResult;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
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
        if (this.studentModuleResult.id !== undefined) {
            this.subscribeToSaveResponse(this.studentModuleResultService.update(this.studentModuleResult));
        } else {
            this.subscribeToSaveResponse(this.studentModuleResultService.create(this.studentModuleResult));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudentModuleResult>>) {
        result.subscribe((res: HttpResponse<IStudentModuleResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackModuleById(index: number, item: IModule) {
        return item.id;
    }
}
