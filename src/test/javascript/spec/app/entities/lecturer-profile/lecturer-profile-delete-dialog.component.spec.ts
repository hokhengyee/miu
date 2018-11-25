/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { LecturerProfileDeleteDialogComponent } from 'app/entities/lecturer-profile/lecturer-profile-delete-dialog.component';
import { LecturerProfileService } from 'app/entities/lecturer-profile/lecturer-profile.service';

describe('Component Tests', () => {
    describe('LecturerProfile Management Delete Component', () => {
        let comp: LecturerProfileDeleteDialogComponent;
        let fixture: ComponentFixture<LecturerProfileDeleteDialogComponent>;
        let service: LecturerProfileService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [LecturerProfileDeleteDialogComponent]
            })
                .overrideTemplate(LecturerProfileDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LecturerProfileDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturerProfileService);
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
