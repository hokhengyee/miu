import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGender } from 'app/shared/model/gender.model';

@Component({
    selector: 'jhi-gender-detail',
    templateUrl: './gender-detail.component.html'
})
export class GenderDetailComponent implements OnInit {
    gender: IGender;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gender }) => {
            this.gender = gender;
        });
    }

    previousState() {
        window.history.back();
    }
}
