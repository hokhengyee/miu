import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommonResources } from 'app/shared/model/common-resources.model';
import { CommonResourcesService } from './common-resources.service';

@Component({
    selector: 'jhi-common-resources-update',
    templateUrl: './common-resources-update.component.html'
})
export class CommonResourcesUpdateComponent implements OnInit {
    commonResources: ICommonResources;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private commonResourcesService: CommonResourcesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commonResources.id !== undefined) {
            this.subscribeToSaveResponse(this.commonResourcesService.update(this.commonResources));
        } else {
            this.subscribeToSaveResponse(this.commonResourcesService.create(this.commonResources));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICommonResources>>) {
        result.subscribe((res: HttpResponse<ICommonResources>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
