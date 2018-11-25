import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IGallery } from 'app/shared/model/gallery.model';
import { GalleryService } from './gallery.service';

@Component({
    selector: 'jhi-gallery-update',
    templateUrl: './gallery-update.component.html'
})
export class GalleryUpdateComponent implements OnInit {
    gallery: IGallery;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private galleryService: GalleryService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gallery }) => {
            this.gallery = gallery;
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.gallery, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gallery.id !== undefined) {
            this.subscribeToSaveResponse(this.galleryService.update(this.gallery));
        } else {
            this.subscribeToSaveResponse(this.galleryService.create(this.gallery));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGallery>>) {
        result.subscribe((res: HttpResponse<IGallery>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
