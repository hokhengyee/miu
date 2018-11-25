import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INewsAndEvent } from 'app/shared/model/news-and-event.model';

@Component({
    selector: 'jhi-news-and-event-detail',
    templateUrl: './news-and-event-detail.component.html'
})
export class NewsAndEventDetailComponent implements OnInit {
    newsAndEvent: INewsAndEvent;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ newsAndEvent }) => {
            this.newsAndEvent = newsAndEvent;
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
