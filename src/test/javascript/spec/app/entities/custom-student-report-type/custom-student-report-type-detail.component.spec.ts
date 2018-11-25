/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CustomStudentReportTypeDetailComponent } from 'app/entities/custom-student-report-type/custom-student-report-type-detail.component';
import { CustomStudentReportType } from 'app/shared/model/custom-student-report-type.model';

describe('Component Tests', () => {
    describe('CustomStudentReportType Management Detail Component', () => {
        let comp: CustomStudentReportTypeDetailComponent;
        let fixture: ComponentFixture<CustomStudentReportTypeDetailComponent>;
        const route = ({ data: of({ customStudentReportType: new CustomStudentReportType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CustomStudentReportTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CustomStudentReportTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CustomStudentReportTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.customStudentReportType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
