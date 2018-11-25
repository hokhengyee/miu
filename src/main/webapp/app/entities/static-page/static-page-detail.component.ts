import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStaticPage } from 'app/shared/model/static-page.model';

@Component({
    selector: 'jhi-static-page-detail',
    templateUrl: './static-page-detail.component.html'
})
export class StaticPageDetailComponent implements OnInit {
    staticPage: IStaticPage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ staticPage }) => {
            this.staticPage = staticPage;
        });
    }

    previousState() {
        window.history.back();
    }
}
