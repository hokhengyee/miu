import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IStudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';
import { StudentResearchPaperResultService } from './student-research-paper-result.service';
import { IResearchPaper } from 'app/shared/model/research-paper.model';
import { ResearchPaperService } from 'app/entities/research-paper';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-student-research-paper-result-update',
    templateUrl: './student-research-paper-result-update.component.html'
})
export class StudentResearchPaperResultUpdateComponent implements OnInit {
    studentResearchPaperResult: IStudentResearchPaperResult;
    isSaving: boolean;

    researchpapers: IResearchPaper[];

    users: IUser[];
    dateGradedDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private studentResearchPaperResultService: StudentResearchPaperResultService,
        private researchPaperService: ResearchPaperService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentResearchPaperResult }) => {
            this.studentResearchPaperResult = studentResearchPaperResult;
        });
        this.researchPaperService.query().subscribe(
            (res: HttpResponse<IResearchPaper[]>) => {
                this.researchpapers = res.body;
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
        if (this.studentResearchPaperResult.id !== undefined) {
            this.subscribeToSaveResponse(this.studentResearchPaperResultService.update(this.studentResearchPaperResult));
        } else {
            this.subscribeToSaveResponse(this.studentResearchPaperResultService.create(this.studentResearchPaperResult));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudentResearchPaperResult>>) {
        result.subscribe(
            (res: HttpResponse<IStudentResearchPaperResult>) => this.onSaveSuccess(),
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

    trackResearchPaperById(index: number, item: IResearchPaper) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
