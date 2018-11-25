import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStudentProfile } from 'app/shared/model/student-profile.model';

@Component({
    selector: 'jhi-student-profile-detail',
    templateUrl: './student-profile-detail.component.html'
})
export class StudentProfileDetailComponent implements OnInit {
    studentProfile: IStudentProfile;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentProfile }) => {
            this.studentProfile = studentProfile;
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
