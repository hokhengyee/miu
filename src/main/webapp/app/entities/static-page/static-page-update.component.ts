import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStaticPage } from 'app/shared/model/static-page.model';
import { StaticPageService } from './static-page.service';
import { IStaticPageType } from 'app/shared/model/static-page-type.model';
import { StaticPageTypeService } from 'app/entities/static-page-type';

@Component({
    selector: 'jhi-static-page-update',
    templateUrl: './static-page-update.component.html'
})
export class StaticPageUpdateComponent implements OnInit {
    staticPage: IStaticPage;
    isSaving: boolean;

    staticpagetypes: IStaticPageType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private staticPageService: StaticPageService,
        private staticPageTypeService: StaticPageTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ staticPage }) => {
            this.staticPage = staticPage;
        });
        this.staticPageTypeService.query().subscribe(
            (res: HttpResponse<IStaticPageType[]>) => {
                this.staticpagetypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.staticPage.id !== undefined) {
            this.subscribeToSaveResponse(this.staticPageService.update(this.staticPage));
        } else {
            this.subscribeToSaveResponse(this.staticPageService.create(this.staticPage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStaticPage>>) {
        result.subscribe((res: HttpResponse<IStaticPage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStaticPageTypeById(index: number, item: IStaticPageType) {
        return item.id;
    }
}
