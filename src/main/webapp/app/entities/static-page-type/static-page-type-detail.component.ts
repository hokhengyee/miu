import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStaticPageType } from 'app/shared/model/static-page-type.model';

@Component({
    selector: 'jhi-static-page-type-detail',
    templateUrl: './static-page-type-detail.component.html'
})
export class StaticPageTypeDetailComponent implements OnInit {
    staticPageType: IStaticPageType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ staticPageType }) => {
            this.staticPageType = staticPageType;
        });
    }

    previousState() {
        window.history.back();
    }
}
