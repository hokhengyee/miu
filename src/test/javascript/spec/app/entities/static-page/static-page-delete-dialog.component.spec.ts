/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { StaticPageDeleteDialogComponent } from 'app/entities/static-page/static-page-delete-dialog.component';
import { StaticPageService } from 'app/entities/static-page/static-page.service';

describe('Component Tests', () => {
    describe('StaticPage Management Delete Component', () => {
        let comp: StaticPageDeleteDialogComponent;
        let fixture: ComponentFixture<StaticPageDeleteDialogComponent>;
        let service: StaticPageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StaticPageDeleteDialogComponent]
            })
                .overrideTemplate(StaticPageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StaticPageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StaticPageService);
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
