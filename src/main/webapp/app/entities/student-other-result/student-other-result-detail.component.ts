import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentOtherResult } from 'app/shared/model/student-other-result.model';

@Component({
    selector: 'jhi-student-other-result-detail',
    templateUrl: './student-other-result-detail.component.html'
})
export class StudentOtherResultDetailComponent implements OnInit {
    studentOtherResult: IStudentOtherResult;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentOtherResult }) => {
            this.studentOtherResult = studentOtherResult;
        });
    }

    previousState() {
        window.history.back();
    }
}
