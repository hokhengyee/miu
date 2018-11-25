import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IResearchPaper } from 'app/shared/model/research-paper.model';
import { ResearchPaperService } from './research-paper.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-research-paper-update',
    templateUrl: './research-paper-update.component.html'
})
export class ResearchPaperUpdateComponent implements OnInit {
    researchPaper: IResearchPaper;
    isSaving: boolean;

    courses: ICourse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private researchPaperService: ResearchPaperService,
        private courseService: CourseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ researchPaper }) => {
            this.researchPaper = researchPaper;
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
        if (this.researchPaper.id !== undefined) {
            this.subscribeToSaveResponse(this.researchPaperService.update(this.researchPaper));
        } else {
            this.subscribeToSaveResponse(this.researchPaperService.create(this.researchPaper));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResearchPaper>>) {
        result.subscribe((res: HttpResponse<IResearchPaper>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
