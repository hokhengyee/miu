/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { RegistrationAcademicDetailsDetailComponent } from 'app/entities/registration-academic-details/registration-academic-details-detail.component';
import { RegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';

describe('Component Tests', () => {
    describe('RegistrationAcademicDetails Management Detail Component', () => {
        let comp: RegistrationAcademicDetailsDetailComponent;
        let fixture: ComponentFixture<RegistrationAcademicDetailsDetailComponent>;
        const route = ({ data: of({ registrationAcademicDetails: new RegistrationAcademicDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [RegistrationAcademicDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegistrationAcademicDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegistrationAcademicDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.registrationAcademicDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
