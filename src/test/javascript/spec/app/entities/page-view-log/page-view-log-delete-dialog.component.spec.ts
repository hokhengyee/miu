/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { PageViewLogDeleteDialogComponent } from 'app/entities/page-view-log/page-view-log-delete-dialog.component';
import { PageViewLogService } from 'app/entities/page-view-log/page-view-log.service';

describe('Component Tests', () => {
    describe('PageViewLog Management Delete Component', () => {
        let comp: PageViewLogDeleteDialogComponent;
        let fixture: ComponentFixture<PageViewLogDeleteDialogComponent>;
        let service: PageViewLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [PageViewLogDeleteDialogComponent]
            })
                .overrideTemplate(PageViewLogDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PageViewLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PageViewLogService);
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
