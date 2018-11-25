/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { RegistrationAcademicDetailsDeleteDialogComponent } from 'app/entities/registration-academic-details/registration-academic-details-delete-dialog.component';
import { RegistrationAcademicDetailsService } from 'app/entities/registration-academic-details/registration-academic-details.service';

describe('Component Tests', () => {
    describe('RegistrationAcademicDetails Management Delete Component', () => {
        let comp: RegistrationAcademicDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<RegistrationAcademicDetailsDeleteDialogComponent>;
        let service: RegistrationAcademicDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [RegistrationAcademicDetailsDeleteDialogComponent]
            })
                .overrideTemplate(RegistrationAcademicDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegistrationAcademicDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegistrationAcademicDetailsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
