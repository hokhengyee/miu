/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentPaymentDetailComponent } from 'app/entities/student-payment/student-payment-detail.component';
import { StudentPayment } from 'app/shared/model/student-payment.model';

describe('Component Tests', () => {
    describe('StudentPayment Management Detail Component', () => {
        let comp: StudentPaymentDetailComponent;
        let fixture: ComponentFixture<StudentPaymentDetailComponent>;
        const route = ({ data: of({ studentPayment: new StudentPayment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentPaymentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentPaymentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentPaymentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentPayment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
