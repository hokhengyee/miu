import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAcademicCertificate } from 'app/shared/model/academic-certificate.model';

@Component({
    selector: 'jhi-academic-certificate-detail',
    templateUrl: './academic-certificate-detail.component.html'
})
export class AcademicCertificateDetailComponent implements OnInit {
    academicCertificate: IAcademicCertificate;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ academicCertificate }) => {
            this.academicCertificate = academicCertificate;
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
