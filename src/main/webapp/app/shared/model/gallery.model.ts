export interface IGallery {
    id?: number;
    imageTitle?: string;
    galleryPhotoContentType?: string;
    galleryPhoto?: any;
}

export class Gallery implements IGallery {
    constructor(public id?: number, public imageTitle?: string, public galleryPhotoContentType?: string, public galleryPhoto?: any) {}
}
