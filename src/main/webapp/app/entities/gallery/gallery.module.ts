import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiuSharedModule } from 'app/shared';
import {
    GalleryComponent,
    GalleryDetailComponent,
    GalleryUpdateComponent,
    GalleryDeletePopupComponent,
    GalleryDeleteDialogComponent,
    galleryRoute,
    galleryPopupRoute
} from './';

const ENTITY_STATES = [...galleryRoute, ...galleryPopupRoute];

@NgModule({
    imports: [MiuSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GalleryComponent,
        GalleryDetailComponent,
        GalleryUpdateComponent,
        GalleryDeleteDialogComponent,
        GalleryDeletePopupComponent
    ],
    entryComponents: [GalleryComponent, GalleryUpdateComponent, GalleryDeleteDialogComponent, GalleryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuGalleryModule {}
