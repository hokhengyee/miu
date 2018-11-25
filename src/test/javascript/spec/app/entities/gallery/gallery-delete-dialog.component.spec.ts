/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { GalleryDeleteDialogComponent } from 'app/entities/gallery/gallery-delete-dialog.component';
import { GalleryService } from 'app/entities/gallery/gallery.service';

describe('Component Tests', () => {
    describe('Gallery Management Delete Component', () => {
        let comp: GalleryDeleteDialogComponent;
        let fixture: ComponentFixture<GalleryDeleteDialogComponent>;
        let service: GalleryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [GalleryDeleteDialogComponent]
            })
                .overrideTemplate(GalleryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GalleryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryService);
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
