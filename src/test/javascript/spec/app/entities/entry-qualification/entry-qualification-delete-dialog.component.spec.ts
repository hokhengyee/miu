/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { EntryQualificationDeleteDialogComponent } from 'app/entities/entry-qualification/entry-qualification-delete-dialog.component';
import { EntryQualificationService } from 'app/entities/entry-qualification/entry-qualification.service';

describe('Component Tests', () => {
    describe('EntryQualification Management Delete Component', () => {
        let comp: EntryQualificationDeleteDialogComponent;
        let fixture: ComponentFixture<EntryQualificationDeleteDialogComponent>;
        let service: EntryQualificationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [EntryQualificationDeleteDialogComponent]
            })
                .overrideTemplate(EntryQualificationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntryQualificationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntryQualificationService);
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
