import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IStaticPageType } from 'app/shared/model/static-page-type.model';
import { StaticPageTypeService } from './static-page-type.service';

@Component({
    selector: 'jhi-static-page-type-update',
    templateUrl: './static-page-type-update.component.html'
})
export class StaticPageTypeUpdateComponent implements OnInit {
    staticPageType: IStaticPageType;
    isSaving: boolean;

    constructor(private staticPageTypeService: StaticPageTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ staticPageType }) => {
            this.staticPageType = staticPageType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.staticPageType.id !== undefined) {
            this.subscribeToSaveResponse(this.staticPageTypeService.update(this.staticPageType));
        } else {
            this.subscribeToSaveResponse(this.staticPageTypeService.create(this.staticPageType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStaticPageType>>) {
        result.subscribe((res: HttpResponse<IStaticPageType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
