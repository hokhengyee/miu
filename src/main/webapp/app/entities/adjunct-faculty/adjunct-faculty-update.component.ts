import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';
import { AdjunctFacultyService } from './adjunct-faculty.service';
import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';
import { LecturerProfileService } from 'app/entities/lecturer-profile';

@Component({
    selector: 'jhi-adjunct-faculty-update',
    templateUrl: './adjunct-faculty-update.component.html'
})
export class AdjunctFacultyUpdateComponent implements OnInit {
    adjunctFaculty: IAdjunctFaculty;
    isSaving: boolean;

    lecturerprofiles: ILecturerProfile[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adjunctFacultyService: AdjunctFacultyService,
        private lecturerProfileService: LecturerProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adjunctFaculty }) => {
            this.adjunctFaculty = adjunctFaculty;
        });
        this.lecturerProfileService.query().subscribe(
            (res: HttpResponse<ILecturerProfile[]>) => {
                this.lecturerprofiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adjunctFaculty.id !== undefined) {
            this.subscribeToSaveResponse(this.adjunctFacultyService.update(this.adjunctFaculty));
        } else {
            this.subscribeToSaveResponse(this.adjunctFacultyService.create(this.adjunctFaculty));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdjunctFaculty>>) {
        result.subscribe((res: HttpResponse<IAdjunctFaculty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLecturerProfileById(index: number, item: ILecturerProfile) {
        return item.id;
    }
}
