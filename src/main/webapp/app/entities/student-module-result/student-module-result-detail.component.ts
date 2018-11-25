import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentModuleResult } from 'app/shared/model/student-module-result.model';

@Component({
    selector: 'jhi-student-module-result-detail',
    templateUrl: './student-module-result-detail.component.html'
})
export class StudentModuleResultDetailComponent implements OnInit {
    studentModuleResult: IStudentModuleResult;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModuleResult }) => {
            this.studentModuleResult = studentModuleResult;
        });
    }

    previousState() {
        window.history.back();
    }
}
