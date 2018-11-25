import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISalutation } from 'app/shared/model/salutation.model';
import { SalutationService } from './salutation.service';

@Component({
    selector: 'jhi-salutation-update',
    templateUrl: './salutation-update.component.html'
})
export class SalutationUpdateComponent implements OnInit {
    salutation: ISalutation;
    isSaving: boolean;

    constructor(private salutationService: SalutationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salutation }) => {
            this.salutation = salutation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.salutation.id !== undefined) {
            this.subscribeToSaveResponse(this.salutationService.update(this.salutation));
        } else {
            this.subscribeToSaveResponse(this.salutationService.create(this.salutation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISalutation>>) {
        result.subscribe((res: HttpResponse<ISalutation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
