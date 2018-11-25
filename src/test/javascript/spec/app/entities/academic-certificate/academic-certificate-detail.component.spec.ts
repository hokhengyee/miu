/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { AcademicCertificateDetailComponent } from 'app/entities/academic-certificate/academic-certificate-detail.component';
import { AcademicCertificate } from 'app/shared/model/academic-certificate.model';

describe('Component Tests', () => {
    describe('AcademicCertificate Management Detail Component', () => {
        let comp: AcademicCertificateDetailComponent;
        let fixture: ComponentFixture<AcademicCertificateDetailComponent>;
        const route = ({ data: of({ academicCertificate: new AcademicCertificate(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [AcademicCertificateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AcademicCertificateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcademicCertificateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.academicCertificate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
