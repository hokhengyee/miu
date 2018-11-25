import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourseAccess } from 'app/shared/model/course-access.model';

@Component({
    selector: 'jhi-course-access-detail',
    templateUrl: './course-access-detail.component.html'
})
export class CourseAccessDetailComponent implements OnInit {
    courseAccess: ICourseAccess;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courseAccess }) => {
            this.courseAccess = courseAccess;
        });
    }

    previousState() {
        window.history.back();
    }
}
