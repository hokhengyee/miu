/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { AdjunctFacultyDeleteDialogComponent } from 'app/entities/adjunct-faculty/adjunct-faculty-delete-dialog.component';
import { AdjunctFacultyService } from 'app/entities/adjunct-faculty/adjunct-faculty.service';

describe('Component Tests', () => {
    describe('AdjunctFaculty Management Delete Component', () => {
        let comp: AdjunctFacultyDeleteDialogComponent;
        let fixture: ComponentFixture<AdjunctFacultyDeleteDialogComponent>;
        let service: AdjunctFacultyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [AdjunctFacultyDeleteDialogComponent]
            })
                .overrideTemplate(AdjunctFacultyDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdjunctFacultyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdjunctFacultyService);
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
