import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';

@Component({
    selector: 'jhi-registration-academic-details-detail',
    templateUrl: './registration-academic-details-detail.component.html'
})
export class RegistrationAcademicDetailsDetailComponent implements OnInit {
    registrationAcademicDetails: IRegistrationAcademicDetails;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ registrationAcademicDetails }) => {
            this.registrationAcademicDetails = registrationAcademicDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
