import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';
import { RegistrationAcademicDetailsService } from './registration-academic-details.service';
import { RegistrationAcademicDetailsComponent } from './registration-academic-details.component';
import { RegistrationAcademicDetailsDetailComponent } from './registration-academic-details-detail.component';
import { RegistrationAcademicDetailsUpdateComponent } from './registration-academic-details-update.component';
import { RegistrationAcademicDetailsDeletePopupComponent } from './registration-academic-details-delete-dialog.component';
import { IRegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';

@Injectable({ providedIn: 'root' })
export class RegistrationAcademicDetailsResolve implements Resolve<IRegistrationAcademicDetails> {
    constructor(private service: RegistrationAcademicDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RegistrationAcademicDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RegistrationAcademicDetails>) => response.ok),
                map((registrationAcademicDetails: HttpResponse<RegistrationAcademicDetails>) => registrationAcademicDetails.body)
            );
        }
        return of(new RegistrationAcademicDetails());
    }
}

export const registrationAcademicDetailsRoute: Routes = [
    {
        path: 'registration-academic-details',
        component: RegistrationAcademicDetailsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RegistrationAcademicDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registration-academic-details/:id/view',
        component: RegistrationAcademicDetailsDetailComponent,
        resolve: {
            registrationAcademicDetails: RegistrationAcademicDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegistrationAcademicDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registration-academic-details/new',
        component: RegistrationAcademicDetailsUpdateComponent,
        resolve: {
            registrationAcademicDetails: RegistrationAcademicDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegistrationAcademicDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registration-academic-details/:id/edit',
        component: RegistrationAcademicDetailsUpdateComponent,
        resolve: {
            registrationAcademicDetails: RegistrationAcademicDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegistrationAcademicDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const registrationAcademicDetailsPopupRoute: Routes = [
    {
        path: 'registration-academic-details/:id/delete',
        component: RegistrationAcademicDetailsDeletePopupComponent,
        resolve: {
            registrationAcademicDetails: RegistrationAcademicDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegistrationAcademicDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
