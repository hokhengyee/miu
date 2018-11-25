import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResearchPaper } from 'app/shared/model/research-paper.model';

@Component({
    selector: 'jhi-research-paper-detail',
    templateUrl: './research-paper-detail.component.html'
})
export class ResearchPaperDetailComponent implements OnInit {
    researchPaper: IResearchPaper;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ researchPaper }) => {
            this.researchPaper = researchPaper;
        });
    }

    previousState() {
        window.history.back();
    }
}
