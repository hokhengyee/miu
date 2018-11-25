import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';

@Component({
    selector: 'jhi-adjunct-faculty-detail',
    templateUrl: './adjunct-faculty-detail.component.html'
})
export class AdjunctFacultyDetailComponent implements OnInit {
    adjunctFaculty: IAdjunctFaculty;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adjunctFaculty }) => {
            this.adjunctFaculty = adjunctFaculty;
        });
    }

    previousState() {
        window.history.back();
    }
}
