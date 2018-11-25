import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';

@Component({
    selector: 'jhi-student-research-paper-result-detail',
    templateUrl: './student-research-paper-result-detail.component.html'
})
export class StudentResearchPaperResultDetailComponent implements OnInit {
    studentResearchPaperResult: IStudentResearchPaperResult;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentResearchPaperResult }) => {
            this.studentResearchPaperResult = studentResearchPaperResult;
        });
    }

    previousState() {
        window.history.back();
    }
}
