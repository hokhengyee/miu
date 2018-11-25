/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MiuTestModule } from '../../../test.module';
import { StudentResearchPaperResultDeleteDialogComponent } from 'app/entities/student-research-paper-result/student-research-paper-result-delete-dialog.component';
import { StudentResearchPaperResultService } from 'app/entities/student-research-paper-result/student-research-paper-result.service';

describe('Component Tests', () => {
    describe('StudentResearchPaperResult Management Delete Component', () => {
        let comp: StudentResearchPaperResultDeleteDialogComponent;
        let fixture: ComponentFixture<StudentResearchPaperResultDeleteDialogComponent>;
        let service: StudentResearchPaperResultService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentResearchPaperResultDeleteDialogComponent]
            })
                .overrideTemplate(StudentResearchPaperResultDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentResearchPaperResultDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentResearchPaperResultService);
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
