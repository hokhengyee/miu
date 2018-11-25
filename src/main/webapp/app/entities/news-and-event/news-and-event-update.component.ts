import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils } from 'ng-jhipster';

import { INewsAndEvent } from 'app/shared/model/news-and-event.model';
import { NewsAndEventService } from './news-and-event.service';

@Component({
    selector: 'jhi-news-and-event-update',
    templateUrl: './news-and-event-update.component.html'
})
export class NewsAndEventUpdateComponent implements OnInit {
    newsAndEvent: INewsAndEvent;
    isSaving: boolean;
    startDT: string;
    endDT: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private newsAndEventService: NewsAndEventService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ newsAndEvent }) => {
            this.newsAndEvent = newsAndEvent;
            this.startDT = this.newsAndEvent.startDT != null ? this.newsAndEvent.startDT.format(DATE_TIME_FORMAT) : null;
            this.endDT = this.newsAndEvent.endDT != null ? this.newsAndEvent.endDT.format(DATE_TIME_FORMAT) : null;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.newsAndEvent.startDT = this.startDT != null ? moment(this.startDT, DATE_TIME_FORMAT) : null;
        this.newsAndEvent.endDT = this.endDT != null ? moment(this.endDT, DATE_TIME_FORMAT) : null;
        if (this.newsAndEvent.id !== undefined) {
            this.subscribeToSaveResponse(this.newsAndEventService.update(this.newsAndEvent));
        } else {
            this.subscribeToSaveResponse(this.newsAndEventService.create(this.newsAndEvent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INewsAndEvent>>) {
        result.subscribe((res: HttpResponse<INewsAndEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
