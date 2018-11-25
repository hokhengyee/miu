import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentProfile } from 'app/shared/model/student-profile.model';
import { StudentProfileService } from './student-profile.service';
import { StudentProfileComponent } from './student-profile.component';
import { StudentProfileDetailComponent } from './student-profile-detail.component';
import { StudentProfileUpdateComponent } from './student-profile-update.component';
import { StudentProfileDeletePopupComponent } from './student-profile-delete-dialog.component';
import { IStudentProfile } from 'app/shared/model/student-profile.model';

@Injectable({ providedIn: 'root' })
export class StudentProfileResolve implements Resolve<IStudentProfile> {
    constructor(private service: StudentProfileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StudentProfile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentProfile>) => response.ok),
                map((studentProfile: HttpResponse<StudentProfile>) => studentProfile.body)
            );
        }
        return of(new StudentProfile());
    }
}

export const studentProfileRoute: Routes = [
    {
        path: 'student-profile',
        component: StudentProfileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StudentProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-profile/:id/view',
        component: StudentProfileDetailComponent,
        resolve: {
            studentProfile: StudentProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-profile/new',
        component: StudentProfileUpdateComponent,
        resolve: {
            studentProfile: StudentProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-profile/:id/edit',
        component: StudentProfileUpdateComponent,
        resolve: {
            studentProfile: StudentProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentProfiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentProfilePopupRoute: Routes = [
    {
        path: 'student-profile/:id/delete',
        component: StudentProfileDeletePopupComponent,
        resolve: {
            studentProfile: StudentProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentProfiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
