import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOnlineApplication } from 'app/shared/model/online-application.model';

@Component({
    selector: 'jhi-online-application-detail',
    templateUrl: './online-application-detail.component.html'
})
export class OnlineApplicationDetailComponent implements OnInit {
    onlineApplication: IOnlineApplication;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ onlineApplication }) => {
            this.onlineApplication = onlineApplication;
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
