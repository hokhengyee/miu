/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { CourseAccessDeleteDialogComponent } from 'app/entities/course-access/course-access-delete-dialog.component';
import { CourseAccessService } from 'app/entities/course-access/course-access.service';

describe('Component Tests', () => {
    describe('CourseAccess Management Delete Component', () => {
        let comp: CourseAccessDeleteDialogComponent;
        let fixture: ComponentFixture<CourseAccessDeleteDialogComponent>;
        let service: CourseAccessService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseAccessDeleteDialogComponent]
            })
                .overrideTemplate(CourseAccessDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourseAccessDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseAccessService);
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
