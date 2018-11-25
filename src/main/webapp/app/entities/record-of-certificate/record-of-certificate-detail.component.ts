import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecordOfCertificate } from 'app/shared/model/record-of-certificate.model';

@Component({
    selector: 'jhi-record-of-certificate-detail',
    templateUrl: './record-of-certificate-detail.component.html'
})
export class RecordOfCertificateDetailComponent implements OnInit {
    recordOfCertificate: IRecordOfCertificate;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recordOfCertificate }) => {
            this.recordOfCertificate = recordOfCertificate;
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
