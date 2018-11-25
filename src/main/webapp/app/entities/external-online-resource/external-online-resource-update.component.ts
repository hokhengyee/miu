import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IExternalOnlineResource } from 'app/shared/model/external-online-resource.model';
import { ExternalOnlineResourceService } from './external-online-resource.service';

@Component({
    selector: 'jhi-external-online-resource-update',
    templateUrl: './external-online-resource-update.component.html'
})
export class ExternalOnlineResourceUpdateComponent implements OnInit {
    externalOnlineResource: IExternalOnlineResource;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private externalOnlineResourceService: ExternalOnlineResourceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.externalOnlineResource.id !== undefined) {
            this.subscribeToSaveResponse(this.externalOnlineResourceService.update(this.externalOnlineResource));
        } else {
            this.subscribeToSaveResponse(this.externalOnlineResourceService.create(this.externalOnlineResource));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IExternalOnlineResource>>) {
        result.subscribe(
            (res: HttpResponse<IExternalOnlineResource>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
