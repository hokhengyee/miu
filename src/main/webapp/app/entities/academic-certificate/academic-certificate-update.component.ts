import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IAcademicCertificate } from 'app/shared/model/academic-certificate.model';
import { AcademicCertificateService } from './academic-certificate.service';

@Component({
    selector: 'jhi-academic-certificate-update',
    templateUrl: './academic-certificate-update.component.html'
})
export class AcademicCertificateUpdateComponent implements OnInit {
    academicCertificate: IAcademicCertificate;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private academicCertificateService: AcademicCertificateService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ academicCertificate }) => {
            this.academicCertificate = academicCertificate;
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
        if (this.academicCertificate.id !== undefined) {
            this.subscribeToSaveResponse(this.academicCertificateService.update(this.academicCertificate));
        } else {
            this.subscribeToSaveResponse(this.academicCertificateService.create(this.academicCertificate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicCertificate>>) {
        result.subscribe((res: HttpResponse<IAcademicCertificate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
