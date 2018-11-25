import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';
import { MinisterialWorkExperienceService } from './ministerial-work-experience.service';

@Component({
    selector: 'jhi-ministerial-work-experience-update',
    templateUrl: './ministerial-work-experience-update.component.html'
})
export class MinisterialWorkExperienceUpdateComponent implements OnInit {
    ministerialWorkExperience: IMinisterialWorkExperience;
    isSaving: boolean;

    constructor(private ministerialWorkExperienceService: MinisterialWorkExperienceService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ministerialWorkExperience }) => {
            this.ministerialWorkExperience = ministerialWorkExperience;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ministerialWorkExperience.id !== undefined) {
            this.subscribeToSaveResponse(this.ministerialWorkExperienceService.update(this.ministerialWorkExperience));
        } else {
            this.subscribeToSaveResponse(this.ministerialWorkExperienceService.create(this.ministerialWorkExperience));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMinisterialWorkExperience>>) {
        result.subscribe(
            (res: HttpResponse<IMinisterialWorkExperience>) => this.onSaveSuccess(),
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
}
