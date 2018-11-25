import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';
import { LecturerProfileService } from './lecturer-profile.service';
import { IUser, UserService } from 'app/core';
import { ISalutation } from 'app/shared/model/salutation.model';
import { SalutationService } from 'app/entities/salutation';

@Component({
    selector: 'jhi-lecturer-profile-update',
    templateUrl: './lecturer-profile-update.component.html'
})
export class LecturerProfileUpdateComponent implements OnInit {
    lecturerProfile: ILecturerProfile;
    isSaving: boolean;

    users: IUser[];

    salutations: ISalutation[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private lecturerProfileService: LecturerProfileService,
        private userService: UserService,
        private salutationService: SalutationService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lecturerProfile }) => {
            this.lecturerProfile = lecturerProfile;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.salutationService.query().subscribe(
            (res: HttpResponse<ISalutation[]>) => {
                this.salutations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.lecturerProfile, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lecturerProfile.id !== undefined) {
            this.subscribeToSaveResponse(this.lecturerProfileService.update(this.lecturerProfile));
        } else {
            this.subscribeToSaveResponse(this.lecturerProfileService.create(this.lecturerProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILecturerProfile>>) {
        result.subscribe((res: HttpResponse<ILecturerProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackSalutationById(index: number, item: ISalutation) {
        return item.id;
    }
}
