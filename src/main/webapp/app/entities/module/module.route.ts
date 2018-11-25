import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Module } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';
import { ModuleComponent } from './module.component';
import { ModuleDetailComponent } from './module-detail.component';
import { ModuleUpdateComponent } from './module-update.component';
import { ModuleDeletePopupComponent } from './module-delete-dialog.component';
import { IModule } from 'app/shared/model/module.model';

@Injectable({ providedIn: 'root' })
export class ModuleResolve implements Resolve<IModule> {
    constructor(private service: ModuleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Module> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Module>) => response.ok),
                map((module: HttpResponse<Module>) => module.body)
            );
        }
        return of(new Module());
    }
}

export const moduleRoute: Routes = [
    {
        path: 'module',
        component: ModuleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Modules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'module/:id/view',
        component: ModuleDetailComponent,
        resolve: {
            module: ModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Modules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'module/new',
        component: ModuleUpdateComponent,
        resolve: {
            module: ModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Modules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'module/:id/edit',
        component: ModuleUpdateComponent,
        resolve: {
            module: ModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Modules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modulePopupRoute: Routes = [
    {
        path: 'module/:id/delete',
        component: ModuleDeletePopupComponent,
        resolve: {
            module: ModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Modules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
