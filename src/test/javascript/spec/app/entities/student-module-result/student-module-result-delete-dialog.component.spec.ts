/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { StudentModuleResultDeleteDialogComponent } from 'app/entities/student-module-result/student-module-result-delete-dialog.component';
import { StudentModuleResultService } from 'app/entities/student-module-result/student-module-result.service';

describe('Component Tests', () => {
    describe('StudentModuleResult Management Delete Component', () => {
        let comp: StudentModuleResultDeleteDialogComponent;
        let fixture: ComponentFixture<StudentModuleResultDeleteDialogComponent>;
        let service: StudentModuleResultService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentModuleResultDeleteDialogComponent]
            })
                .overrideTemplate(StudentModuleResultDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleResultDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleResultService);
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
