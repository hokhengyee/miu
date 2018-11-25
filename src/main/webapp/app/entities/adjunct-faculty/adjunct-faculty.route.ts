import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';
import { AdjunctFacultyService } from './adjunct-faculty.service';
import { AdjunctFacultyComponent } from './adjunct-faculty.component';
import { AdjunctFacultyDetailComponent } from './adjunct-faculty-detail.component';
import { AdjunctFacultyUpdateComponent } from './adjunct-faculty-update.component';
import { AdjunctFacultyDeletePopupComponent } from './adjunct-faculty-delete-dialog.component';
import { IAdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';

@Injectable({ providedIn: 'root' })
export class AdjunctFacultyResolve implements Resolve<IAdjunctFaculty> {
    constructor(private service: AdjunctFacultyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdjunctFaculty> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdjunctFaculty>) => response.ok),
                map((adjunctFaculty: HttpResponse<AdjunctFaculty>) => adjunctFaculty.body)
            );
        }
        return of(new AdjunctFaculty());
    }
}

export const adjunctFacultyRoute: Routes = [
    {
        path: 'adjunct-faculty',
        component: AdjunctFacultyComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AdjunctFaculties'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adjunct-faculty/:id/view',
        component: AdjunctFacultyDetailComponent,
        resolve: {
            adjunctFaculty: AdjunctFacultyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdjunctFaculties'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adjunct-faculty/new',
        component: AdjunctFacultyUpdateComponent,
        resolve: {
            adjunctFaculty: AdjunctFacultyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdjunctFaculties'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adjunct-faculty/:id/edit',
        component: AdjunctFacultyUpdateComponent,
        resolve: {
            adjunctFaculty: AdjunctFacultyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdjunctFaculties'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adjunctFacultyPopupRoute: Routes = [
    {
        path: 'adjunct-faculty/:id/delete',
        component: AdjunctFacultyDeletePopupComponent,
        resolve: {
            adjunctFaculty: AdjunctFacultyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdjunctFaculties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
