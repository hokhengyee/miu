/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { RecordOfCertificateDetailComponent } from 'app/entities/record-of-certificate/record-of-certificate-detail.component';
import { RecordOfCertificate } from 'app/shared/model/record-of-certificate.model';

describe('Component Tests', () => {
    describe('RecordOfCertificate Management Detail Component', () => {
        let comp: RecordOfCertificateDetailComponent;
        let fixture: ComponentFixture<RecordOfCertificateDetailComponent>;
        const route = ({ data: of({ recordOfCertificate: new RecordOfCertificate(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [RecordOfCertificateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecordOfCertificateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecordOfCertificateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recordOfCertificate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
