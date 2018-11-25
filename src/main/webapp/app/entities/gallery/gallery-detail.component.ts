import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGallery } from 'app/shared/model/gallery.model';

@Component({
    selector: 'jhi-gallery-detail',
    templateUrl: './gallery-detail.component.html'
})
export class GalleryDetailComponent implements OnInit {
    gallery: IGallery;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
