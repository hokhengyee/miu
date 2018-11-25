import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EntryQualification } from 'app/shared/model/entry-qualification.model';
import { EntryQualificationService } from './entry-qualification.service';
import { EntryQualificationComponent } from './entry-qualification.component';
import { EntryQualificationDetailComponent } from './entry-qualification-detail.component';
import { EntryQualificationUpdateComponent } from './entry-qualification-update.component';
import { EntryQualificationDeletePopupComponent } from './entry-qualification-delete-dialog.component';
import { IEntryQualification } from 'app/shared/model/entry-qualification.model';

@Injectable({ providedIn: 'root' })
export class EntryQualificationResolve implements Resolve<IEntryQualification> {
    constructor(private service: EntryQualificationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EntryQualification> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EntryQualification>) => response.ok),
                map((entryQualification: HttpResponse<EntryQualification>) => entryQualification.body)
            );
        }
        return of(new EntryQualification());
    }
}

export const entryQualificationRoute: Routes = [
    {
        path: 'entry-qualification',
        component: EntryQualificationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'EntryQualifications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entry-qualification/:id/view',
        component: EntryQualificationDetailComponent,
        resolve: {
            entryQualification: EntryQualificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntryQualifications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entry-qualification/new',
        component: EntryQualificationUpdateComponent,
        resolve: {
            entryQualification: EntryQualificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntryQualifications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entry-qualification/:id/edit',
        component: EntryQualificationUpdateComponent,
        resolve: {
            entryQualification: EntryQualificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntryQualifications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entryQualificationPopupRoute: Routes = [
    {
        path: 'entry-qualification/:id/delete',
        component: EntryQualificationDeletePopupComponent,
        resolve: {
            entryQualification: EntryQualificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntryQualifications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
