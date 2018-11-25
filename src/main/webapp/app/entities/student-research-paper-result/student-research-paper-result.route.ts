import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';
import { StudentResearchPaperResultService } from './student-research-paper-result.service';
import { StudentResearchPaperResultComponent } from './student-research-paper-result.component';
import { StudentResearchPaperResultDetailComponent } from './student-research-paper-result-detail.component';
import { StudentResearchPaperResultUpdateComponent } from './student-research-paper-result-update.component';
import { StudentResearchPaperResultDeletePopupComponent } from './student-research-paper-result-delete-dialog.component';
import { IStudentResearchPaperResult } from 'app/shared/model/student-research-paper-result.model';

@Injectable({ providedIn: 'root' })
export class StudentResearchPaperResultResolve implements Resolve<IStudentResearchPaperResult> {
    constructor(private service: StudentResearchPaperResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StudentResearchPaperResult> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentResearchPaperResult>) => response.ok),
                map((studentResearchPaperResult: HttpResponse<StudentResearchPaperResult>) => studentResearchPaperResult.body)
            );
        }
        return of(new StudentResearchPaperResult());
    }
}

export const studentResearchPaperResultRoute: Routes = [
    {
        path: 'student-research-paper-result',
        component: StudentResearchPaperResultComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'StudentResearchPaperResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-research-paper-result/:id/view',
        component: StudentResearchPaperResultDetailComponent,
        resolve: {
            studentResearchPaperResult: StudentResearchPaperResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentResearchPaperResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-research-paper-result/new',
        component: StudentResearchPaperResultUpdateComponent,
        resolve: {
            studentResearchPaperResult: StudentResearchPaperResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentResearchPaperResults'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-research-paper-result/:id/edit',
        component: StudentResearchPaperResultUpdateComponent,
        resolve: {
            studentResearchPaperResult: StudentResearchPaperResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentResearchPaperResults'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentResearchPaperResultPopupRoute: Routes = [
    {
        path: 'student-research-paper-result/:id/delete',
        component: StudentResearchPaperResultDeletePopupComponent,
        resolve: {
            studentResearchPaperResult: StudentResearchPaperResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentResearchPaperResults'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
