import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';

@Component({
    selector: 'jhi-ministerial-work-experience-detail',
    templateUrl: './ministerial-work-experience-detail.component.html'
})
export class MinisterialWorkExperienceDetailComponent implements OnInit {
    ministerialWorkExperience: IMinisterialWorkExperience;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ministerialWorkExperience }) => {
            this.ministerialWorkExperience = ministerialWorkExperience;
        });
    }

    previousState() {
        window.history.back();
    }
}
