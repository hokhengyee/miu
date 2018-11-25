/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { CommonResourcesDeleteDialogComponent } from 'app/entities/common-resources/common-resources-delete-dialog.component';
import { CommonResourcesService } from 'app/entities/common-resources/common-resources.service';

describe('Component Tests', () => {
    describe('CommonResources Management Delete Component', () => {
        let comp: CommonResourcesDeleteDialogComponent;
        let fixture: ComponentFixture<CommonResourcesDeleteDialogComponent>;
        let service: CommonResourcesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CommonResourcesDeleteDialogComponent]
            })
                .overrideTemplate(CommonResourcesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommonResourcesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommonResourcesService);
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
