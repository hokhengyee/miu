import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntryQualification } from 'app/shared/model/entry-qualification.model';

@Component({
    selector: 'jhi-entry-qualification-detail',
    templateUrl: './entry-qualification-detail.component.html'
})
export class EntryQualificationDetailComponent implements OnInit {
    entryQualification: IEntryQualification;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entryQualification }) => {
            this.entryQualification = entryQualification;
        });
    }

    previousState() {
        window.history.back();
    }
}
