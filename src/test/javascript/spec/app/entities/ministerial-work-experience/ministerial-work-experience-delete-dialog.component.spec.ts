/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { MinisterialWorkExperienceDeleteDialogComponent } from 'app/entities/ministerial-work-experience/ministerial-work-experience-delete-dialog.component';
import { MinisterialWorkExperienceService } from 'app/entities/ministerial-work-experience/ministerial-work-experience.service';

describe('Component Tests', () => {
    describe('MinisterialWorkExperience Management Delete Component', () => {
        let comp: MinisterialWorkExperienceDeleteDialogComponent;
        let fixture: ComponentFixture<MinisterialWorkExperienceDeleteDialogComponent>;
        let service: MinisterialWorkExperienceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [MinisterialWorkExperienceDeleteDialogComponent]
            })
                .overrideTemplate(MinisterialWorkExperienceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MinisterialWorkExperienceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MinisterialWorkExperienceService);
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
