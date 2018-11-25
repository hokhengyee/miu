import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IModuleType } from 'app/shared/model/module-type.model';
import { ModuleTypeService } from './module-type.service';

@Component({
    selector: 'jhi-module-type-update',
    templateUrl: './module-type-update.component.html'
})
export class ModuleTypeUpdateComponent implements OnInit {
    moduleType: IModuleType;
    isSaving: boolean;

    constructor(private moduleTypeService: ModuleTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moduleType }) => {
            this.moduleType = moduleType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moduleType.id !== undefined) {
            this.subscribeToSaveResponse(this.moduleTypeService.update(this.moduleType));
        } else {
            this.subscribeToSaveResponse(this.moduleTypeService.create(this.moduleType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IModuleType>>) {
        result.subscribe((res: HttpResponse<IModuleType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
