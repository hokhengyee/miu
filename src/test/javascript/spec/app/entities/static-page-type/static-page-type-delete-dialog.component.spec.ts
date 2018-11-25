/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { StaticPageTypeDeleteDialogComponent } from 'app/entities/static-page-type/static-page-type-delete-dialog.component';
import { StaticPageTypeService } from 'app/entities/static-page-type/static-page-type.service';

describe('Component Tests', () => {
    describe('StaticPageType Management Delete Component', () => {
        let comp: StaticPageTypeDeleteDialogComponent;
        let fixture: ComponentFixture<StaticPageTypeDeleteDialogComponent>;
        let service: StaticPageTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StaticPageTypeDeleteDialogComponent]
            })
                .overrideTemplate(StaticPageTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StaticPageTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StaticPageTypeService);
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
