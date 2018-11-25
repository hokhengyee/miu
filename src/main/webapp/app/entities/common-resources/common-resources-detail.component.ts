import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommonResources } from 'app/shared/model/common-resources.model';

@Component({
    selector: 'jhi-common-resources-detail',
    templateUrl: './common-resources-detail.component.html'
})
export class CommonResourcesDetailComponent implements OnInit {
    commonResources: ICommonResources;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commonResources }) => {
            this.commonResources = commonResources;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
