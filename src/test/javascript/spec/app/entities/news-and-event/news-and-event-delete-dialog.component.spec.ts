/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { NewsAndEventDeleteDialogComponent } from 'app/entities/news-and-event/news-and-event-delete-dialog.component';
import { NewsAndEventService } from 'app/entities/news-and-event/news-and-event.service';

describe('Component Tests', () => {
    describe('NewsAndEvent Management Delete Component', () => {
        let comp: NewsAndEventDeleteDialogComponent;
        let fixture: ComponentFixture<NewsAndEventDeleteDialogComponent>;
        let service: NewsAndEventService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [NewsAndEventDeleteDialogComponent]
            })
                .overrideTemplate(NewsAndEventDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NewsAndEventDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NewsAndEventService);
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
