import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';
import { MinisterialWorkExperienceService } from './ministerial-work-experience.service';
import { MinisterialWorkExperienceComponent } from './ministerial-work-experience.component';
import { MinisterialWorkExperienceDetailComponent } from './ministerial-work-experience-detail.component';
import { MinisterialWorkExperienceUpdateComponent } from './ministerial-work-experience-update.component';
import { MinisterialWorkExperienceDeletePopupComponent } from './ministerial-work-experience-delete-dialog.component';
import { IMinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';

@Injectable({ providedIn: 'root' })
export class MinisterialWorkExperienceResolve implements Resolve<IMinisterialWorkExperience> {
    constructor(private service: MinisterialWorkExperienceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MinisterialWorkExperience> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MinisterialWorkExperience>) => response.ok),
                map((ministerialWorkExperience: HttpResponse<MinisterialWorkExperience>) => ministerialWorkExperience.body)
            );
        }
        return of(new MinisterialWorkExperience());
    }
}

export const ministerialWorkExperienceRoute: Routes = [
    {
        path: 'ministerial-work-experience',
        component: MinisterialWorkExperienceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MinisterialWorkExperiences'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ministerial-work-experience/:id/view',
        component: MinisterialWorkExperienceDetailComponent,
        resolve: {
            ministerialWorkExperience: MinisterialWorkExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MinisterialWorkExperiences'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ministerial-work-experience/new',
        component: MinisterialWorkExperienceUpdateComponent,
        resolve: {
            ministerialWorkExperience: MinisterialWorkExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MinisterialWorkExperiences'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ministerial-work-experience/:id/edit',
        component: MinisterialWorkExperienceUpdateComponent,
        resolve: {
            ministerialWorkExperience: MinisterialWorkExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MinisterialWorkExperiences'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ministerialWorkExperiencePopupRoute: Routes = [
    {
        path: 'ministerial-work-experience/:id/delete',
        component: MinisterialWorkExperienceDeletePopupComponent,
        resolve: {
            ministerialWorkExperience: MinisterialWorkExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MinisterialWorkExperiences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
