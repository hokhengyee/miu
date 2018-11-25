import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourseMaterialAccess } from 'app/shared/model/course-material-access.model';

@Component({
    selector: 'jhi-course-material-access-detail',
    templateUrl: './course-material-access-detail.component.html'
})
export class CourseMaterialAccessDetailComponent implements OnInit {
    courseMaterialAccess: ICourseMaterialAccess;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courseMaterialAccess }) => {
            this.courseMaterialAccess = courseMaterialAccess;
        });
    }

    previousState() {
        window.history.back();
    }
}
