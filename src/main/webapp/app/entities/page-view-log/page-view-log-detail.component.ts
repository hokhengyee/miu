import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageViewLog } from 'app/shared/model/page-view-log.model';

@Component({
    selector: 'jhi-page-view-log-detail',
    templateUrl: './page-view-log-detail.component.html'
})
export class PageViewLogDetailComponent implements OnInit {
    pageViewLog: IPageViewLog;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pageViewLog }) => {
            this.pageViewLog = pageViewLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
