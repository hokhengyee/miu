import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalutation } from 'app/shared/model/salutation.model';

@Component({
    selector: 'jhi-salutation-detail',
    templateUrl: './salutation-detail.component.html'
})
export class SalutationDetailComponent implements OnInit {
    salutation: ISalutation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salutation }) => {
            this.salutation = salutation;
        });
    }

    previousState() {
        window.history.back();
    }
}
