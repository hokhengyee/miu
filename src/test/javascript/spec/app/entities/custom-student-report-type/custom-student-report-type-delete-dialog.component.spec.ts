/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { CustomStudentReportTypeDeleteDialogComponent } from 'app/entities/custom-student-report-type/custom-student-report-type-delete-dialog.component';
import { CustomStudentReportTypeService } from 'app/entities/custom-student-report-type/custom-student-report-type.service';

describe('Component Tests', () => {
    describe('CustomStudentReportType Management Delete Component', () => {
        let comp: CustomStudentReportTypeDeleteDialogComponent;
        let fixture: ComponentFixture<CustomStudentReportTypeDeleteDialogComponent>;
        let service: CustomStudentReportTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CustomStudentReportTypeDeleteDialogComponent]
            })
                .overrideTemplate(CustomStudentReportTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CustomStudentReportTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomStudentReportTypeService);
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
