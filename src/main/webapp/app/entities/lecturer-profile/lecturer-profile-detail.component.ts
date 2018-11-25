import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILecturerProfile } from 'app/shared/model/lecturer-profile.model';

@Component({
    selector: 'jhi-lecturer-profile-detail',
    templateUrl: './lecturer-profile-detail.component.html'
})
export class LecturerProfileDetailComponent implements OnInit {
    lecturerProfile: ILecturerProfile;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lecturerProfile }) => {
            this.lecturerProfile = lecturerProfile;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
