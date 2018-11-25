import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Gender } from 'app/shared/model/gender.model';
import { GenderService } from './gender.service';
import { GenderComponent } from './gender.component';
import { GenderDetailComponent } from './gender-detail.component';
import { GenderUpdateComponent } from './gender-update.component';
import { GenderDeletePopupComponent } from './gender-delete-dialog.component';
import { IGender } from 'app/shared/model/gender.model';

@Injectable({ providedIn: 'root' })
export class GenderResolve implements Resolve<IGender> {
    constructor(private service: GenderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Gender> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Gender>) => response.ok),
                map((gender: HttpResponse<Gender>) => gender.body)
            );
        }
        return of(new Gender());
    }
}

export const genderRoute: Routes = [
    {
        path: 'gender',
        component: GenderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gender/:id/view',
        component: GenderDetailComponent,
        resolve: {
            gender: GenderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gender/new',
        component: GenderUpdateComponent,
        resolve: {
            gender: GenderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gender/:id/edit',
        component: GenderUpdateComponent,
        resolve: {
            gender: GenderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genderPopupRoute: Routes = [
    {
        path: 'gender/:id/delete',
        component: GenderDeletePopupComponent,
        resolve: {
            gender: GenderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
