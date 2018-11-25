import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Salutation } from 'app/shared/model/salutation.model';
import { SalutationService } from './salutation.service';
import { SalutationComponent } from './salutation.component';
import { SalutationDetailComponent } from './salutation-detail.component';
import { SalutationUpdateComponent } from './salutation-update.component';
import { SalutationDeletePopupComponent } from './salutation-delete-dialog.component';
import { ISalutation } from 'app/shared/model/salutation.model';

@Injectable({ providedIn: 'root' })
export class SalutationResolve implements Resolve<ISalutation> {
    constructor(private service: SalutationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Salutation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Salutation>) => response.ok),
                map((salutation: HttpResponse<Salutation>) => salutation.body)
            );
        }
        return of(new Salutation());
    }
}

export const salutationRoute: Routes = [
    {
        path: 'salutation',
        component: SalutationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Salutations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'salutation/:id/view',
        component: SalutationDetailComponent,
        resolve: {
            salutation: SalutationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salutations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'salutation/new',
        component: SalutationUpdateComponent,
        resolve: {
            salutation: SalutationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salutations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'salutation/:id/edit',
        component: SalutationUpdateComponent,
        resolve: {
            salutation: SalutationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salutations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salutationPopupRoute: Routes = [
    {
        path: 'salutation/:id/delete',
        component: SalutationDeletePopupComponent,
        resolve: {
            salutation: SalutationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Salutations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
