import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResearchPaper } from 'app/shared/model/research-paper.model';
import { ResearchPaperService } from './research-paper.service';
import { ResearchPaperComponent } from './research-paper.component';
import { ResearchPaperDetailComponent } from './research-paper-detail.component';
import { ResearchPaperUpdateComponent } from './research-paper-update.component';
import { ResearchPaperDeletePopupComponent } from './research-paper-delete-dialog.component';
import { IResearchPaper } from 'app/shared/model/research-paper.model';

@Injectable({ providedIn: 'root' })
export class ResearchPaperResolve implements Resolve<IResearchPaper> {
    constructor(private service: ResearchPaperService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResearchPaper> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ResearchPaper>) => response.ok),
                map((researchPaper: HttpResponse<ResearchPaper>) => researchPaper.body)
            );
        }
        return of(new ResearchPaper());
    }
}

export const researchPaperRoute: Routes = [
    {
        path: 'research-paper',
        component: ResearchPaperComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ResearchPapers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'research-paper/:id/view',
        component: ResearchPaperDetailComponent,
        resolve: {
            researchPaper: ResearchPaperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ResearchPapers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'research-paper/new',
        component: ResearchPaperUpdateComponent,
        resolve: {
            researchPaper: ResearchPaperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ResearchPapers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'research-paper/:id/edit',
        component: ResearchPaperUpdateComponent,
        resolve: {
            researchPaper: ResearchPaperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ResearchPapers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const researchPaperPopupRoute: Routes = [
    {
        path: 'research-paper/:id/delete',
        component: ResearchPaperDeletePopupComponent,
        resolve: {
            researchPaper: ResearchPaperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ResearchPapers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
