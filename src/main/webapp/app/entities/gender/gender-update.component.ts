import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGender } from 'app/shared/model/gender.model';
import { GenderService } from './gender.service';

@Component({
    selector: 'jhi-gender-update',
    templateUrl: './gender-update.component.html'
})
export class GenderUpdateComponent implements OnInit {
    gender: IGender;
    isSaving: boolean;

    constructor(private genderService: GenderService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gender }) => {
            this.gender = gender;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gender.id !== undefined) {
            this.subscribeToSaveResponse(this.genderService.update(this.gender));
        } else {
            this.subscribeToSaveResponse(this.genderService.create(this.gender));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGender>>) {
        result.subscribe((res: HttpResponse<IGender>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
