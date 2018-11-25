/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { CourseMaterialAccessDeleteDialogComponent } from 'app/entities/course-material-access/course-material-access-delete-dialog.component';
import { CourseMaterialAccessService } from 'app/entities/course-material-access/course-material-access.service';

describe('Component Tests', () => {
    describe('CourseMaterialAccess Management Delete Component', () => {
        let comp: CourseMaterialAccessDeleteDialogComponent;
        let fixture: ComponentFixture<CourseMaterialAccessDeleteDialogComponent>;
        let service: CourseMaterialAccessService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseMaterialAccessDeleteDialogComponent]
            })
                .overrideTemplate(CourseMaterialAccessDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourseMaterialAccessDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseMaterialAccessService);
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
