import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Gallery } from 'app/shared/model/gallery.model';
import { GalleryService } from './gallery.service';
import { GalleryComponent } from './gallery.component';
import { GalleryDetailComponent } from './gallery-detail.component';
import { GalleryUpdateComponent } from './gallery-update.component';
import { GalleryDeletePopupComponent } from './gallery-delete-dialog.component';
import { IGallery } from 'app/shared/model/gallery.model';

@Injectable({ providedIn: 'root' })
export class GalleryResolve implements Resolve<IGallery> {
    constructor(private service: GalleryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Gallery> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Gallery>) => response.ok),
                map((gallery: HttpResponse<Gallery>) => gallery.body)
            );
        }
        return of(new Gallery());
    }
}

export const galleryRoute: Routes = [
    {
        path: 'gallery',
        component: GalleryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Galleries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gallery/:id/view',
        component: GalleryDetailComponent,
        resolve: {
            gallery: GalleryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Galleries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gallery/new',
        component: GalleryUpdateComponent,
        resolve: {
            gallery: GalleryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Galleries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gallery/:id/edit',
        component: GalleryUpdateComponent,
        resolve: {
            gallery: GalleryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Galleries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const galleryPopupRoute: Routes = [
    {
        path: 'gallery/:id/delete',
        component: GalleryDeletePopupComponent,
        resolve: {
            gallery: GalleryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Galleries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
