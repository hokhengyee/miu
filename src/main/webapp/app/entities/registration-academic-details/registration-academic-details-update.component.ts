import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';
import { RegistrationAcademicDetailsService } from './registration-academic-details.service';

@Component({
    selector: 'jhi-registration-academic-details-update',
    templateUrl: './registration-academic-details-update.component.html'
})
export class RegistrationAcademicDetailsUpdateComponent implements OnInit {
    registrationAcademicDetails: IRegistrationAcademicDetails;
    isSaving: boolean;

    constructor(private registrationAcademicDetailsService: RegistrationAcademicDetailsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ registrationAcademicDetails }) => {
            this.registrationAcademicDetails = registrationAcademicDetails;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.registrationAcademicDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.registrationAcademicDetailsService.update(this.registrationAcademicDetails));
        } else {
            this.subscribeToSaveResponse(this.registrationAcademicDetailsService.create(this.registrationAcademicDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegistrationAcademicDetails>>) {
        result.subscribe(
            (res: HttpResponse<IRegistrationAcademicDetails>) => this.onSaveSuccess(),
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
