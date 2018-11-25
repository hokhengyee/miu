/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { StudentProfileDeleteDialogComponent } from 'app/entities/student-profile/student-profile-delete-dialog.component';
import { StudentProfileService } from 'app/entities/student-profile/student-profile.service';

describe('Component Tests', () => {
    describe('StudentProfile Management Delete Component', () => {
        let comp: StudentProfileDeleteDialogComponent;
        let fixture: ComponentFixture<StudentProfileDeleteDialogComponent>;
        let service: StudentProfileService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentProfileDeleteDialogComponent]
            })
                .overrideTemplate(StudentProfileDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentProfileDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentProfileService);
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
