/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { ResearchPaperDeleteDialogComponent } from 'app/entities/research-paper/research-paper-delete-dialog.component';
import { ResearchPaperService } from 'app/entities/research-paper/research-paper.service';

describe('Component Tests', () => {
    describe('ResearchPaper Management Delete Component', () => {
        let comp: ResearchPaperDeleteDialogComponent;
        let fixture: ComponentFixture<ResearchPaperDeleteDialogComponent>;
        let service: ResearchPaperService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ResearchPaperDeleteDialogComponent]
            })
                .overrideTemplate(ResearchPaperDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResearchPaperDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResearchPaperService);
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
