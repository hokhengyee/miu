import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecordOfCertificate } from 'app/shared/model/record-of-certificate.model';
import { RecordOfCertificateService } from './record-of-certificate.service';

@Component({
    selector: 'jhi-record-of-certificate-update',
    templateUrl: './record-of-certificate-update.component.html'
})
export class RecordOfCertificateUpdateComponent implements OnInit {
    recordOfCertificate: IRecordOfCertificate;
    isSaving: boolean;
    certDateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private recordOfCertificateService: RecordOfCertificateService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recordOfCertificate }) => {
            this.recordOfCertificate = recordOfCertificate;
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
        if (this.recordOfCertificate.id !== undefined) {
            this.subscribeToSaveResponse(this.recordOfCertificateService.update(this.recordOfCertificate));
        } else {
            this.subscribeToSaveResponse(this.recordOfCertificateService.create(this.recordOfCertificate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecordOfCertificate>>) {
        result.subscribe((res: HttpResponse<IRecordOfCertificate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
