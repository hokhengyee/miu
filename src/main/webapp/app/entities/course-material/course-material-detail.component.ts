import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICourseMaterial } from 'app/shared/model/course-material.model';

@Component({
    selector: 'jhi-course-material-detail',
    templateUrl: './course-material-detail.component.html'
})
export class CourseMaterialDetailComponent implements OnInit {
    courseMaterial: ICourseMaterial;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courseMaterial }) => {
            this.courseMaterial = courseMaterial;
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
