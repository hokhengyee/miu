import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExternalOnlineResource } from 'app/shared/model/external-online-resource.model';

@Component({
    selector: 'jhi-external-online-resource-detail',
    templateUrl: './external-online-resource-detail.component.html'
})
export class ExternalOnlineResourceDetailComponent implements OnInit {
    externalOnlineResource: IExternalOnlineResource;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ externalOnlineResource }) => {
            this.externalOnlineResource = externalOnlineResource;
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
