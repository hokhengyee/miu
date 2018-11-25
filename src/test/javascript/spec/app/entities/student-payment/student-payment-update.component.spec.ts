/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentPaymentUpdateComponent } from 'app/entities/student-payment/student-payment-update.component';
import { StudentPaymentService } from 'app/entities/student-payment/student-payment.service';
import { StudentPayment } from 'app/shared/model/student-payment.model';

describe('Component Tests', () => {
    describe('StudentPayment Management Update Component', () => {
        let comp: StudentPaymentUpdateComponent;
        let fixture: ComponentFixture<StudentPaymentUpdateComponent>;
        let service: StudentPaymentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentPaymentUpdateComponent]
            })
                .overrideTemplate(StudentPaymentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentPaymentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentPaymentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentPayment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentPayment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentPayment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentPayment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
