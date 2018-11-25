import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPageViewLog } from 'app/shared/model/page-view-log.model';
import { PageViewLogService } from './page-view-log.service';

@Component({
    selector: 'jhi-page-view-log-update',
    templateUrl: './page-view-log-update.component.html'
})
export class PageViewLogUpdateComponent implements OnInit {
    pageViewLog: IPageViewLog;
    isSaving: boolean;
    createdDateDp: any;

    constructor(private pageViewLogService: PageViewLogService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pageViewLog }) => {
            this.pageViewLog = pageViewLog;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pageViewLog.id !== undefined) {
            this.subscribeToSaveResponse(this.pageViewLogService.update(this.pageViewLog));
        } else {
            this.subscribeToSaveResponse(this.pageViewLogService.create(this.pageViewLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPageViewLog>>) {
        result.subscribe((res: HttpResponse<IPageViewLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
