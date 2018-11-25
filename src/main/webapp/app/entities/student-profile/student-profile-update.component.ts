import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IStudentProfile } from 'app/shared/model/student-profile.model';
import { StudentProfileService } from './student-profile.service';
import { ISalutation } from 'app/shared/model/salutation.model';
import { SalutationService } from 'app/entities/salutation';
import { IGender } from 'app/shared/model/gender.model';
import { GenderService } from 'app/entities/gender';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-student-profile-update',
    templateUrl: './student-profile-update.component.html'
})
export class StudentProfileUpdateComponent implements OnInit {
    studentProfile: IStudentProfile;
    isSaving: boolean;

    salutations: ISalutation[];

    genders: IGender[];

    users: IUser[];
    dateOfBirthDp: any;
    applicationDateDp: any;
    commencementDateDp: any;
    completionDateDp: any;
    extendedCompletionDateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private studentProfileService: StudentProfileService,
        private salutationService: SalutationService,
        private genderService: GenderService,
        private userService: UserService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentProfile }) => {
            this.studentProfile = studentProfile;
        });
        this.salutationService.query().subscribe(
            (res: HttpResponse<ISalutation[]>) => {
                this.salutations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.genderService.query().subscribe(
            (res: HttpResponse<IGender[]>) => {
                this.genders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
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
        this.dataUtils.clearInputImage(this.studentProfile, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentProfile.id !== undefined) {
            this.subscribeToSaveResponse(this.studentProfileService.update(this.studentProfile));
        } else {
            this.subscribeToSaveResponse(this.studentProfileService.create(this.studentProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudentProfile>>) {
        result.subscribe((res: HttpResponse<IStudentProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSalutationById(index: number, item: ISalutation) {
        return item.id;
    }

    trackGenderById(index: number, item: IGender) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
