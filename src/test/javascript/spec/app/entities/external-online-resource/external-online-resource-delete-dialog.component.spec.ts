/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { ExternalOnlineResourceDeleteDialogComponent } from 'app/entities/external-online-resource/external-online-resource-delete-dialog.component';
import { ExternalOnlineResourceService } from 'app/entities/external-online-resource/external-online-resource.service';

describe('Component Tests', () => {
    describe('ExternalOnlineResource Management Delete Component', () => {
        let comp: ExternalOnlineResourceDeleteDialogComponent;
        let fixture: ComponentFixture<ExternalOnlineResourceDeleteDialogComponent>;
        let service: ExternalOnlineResourceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ExternalOnlineResourceDeleteDialogComponent]
            })
                .overrideTemplate(ExternalOnlineResourceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExternalOnlineResourceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExternalOnlineResourceService);
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
