import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LecturerProfile } from 'app/shared/model/lecturer-profile.model';
import { LecturerProfileService } from './lecturer-profile.service';
import { LecturerProfileComponent } from './lecturer-profile.component';
import { LecturerProfileDetailComponent } from './lecturer-profile-detail.component';
import { LecturerProfileUpdateComponent } from './lecturer-profile-update.component';
import { LecturerProfileDeletePopupComponent } from './lecturer-profile-delete-dialog.component';
import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';

@Injectable({ providedIn: 'root' })
export class LecturerProfileResolve implements Resolve<ILecturerProfile> {
    constructor(private service: LecturerProfileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<LecturerProfile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LecturerProfile>) => response.ok),
                map((lecturerProfile: HttpResponse<LecturerProfile>) => lecturerProfile.body)
            );
        }
        return of(new LecturerProfile());
    }
}

export const lecturerProfileRoute: Routes = [
    {
        path: 'lecturer-profile',
        component: LecturerProfileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'LecturerProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lecturer-profile/:id/view',
        component: LecturerProfileDetailComponent,
        resolve: {
            lecturerProfile: LecturerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LecturerProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lecturer-profile/new',
        component: LecturerProfileUpdateComponent,
        resolve: {
            lecturerProfile: LecturerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LecturerProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lecturer-profile/:id/edit',
        component: LecturerProfileUpdateComponent,
        resolve: {
            lecturerProfile: LecturerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LecturerProfiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lecturerProfilePopupRoute: Routes = [
    {
        path: 'lecturer-profile/:id/delete',
        component: LecturerProfileDeletePopupComponent,
        resolve: {
            lecturerProfile: LecturerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LecturerProfiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
