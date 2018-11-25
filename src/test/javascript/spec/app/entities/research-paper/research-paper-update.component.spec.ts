/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ResearchPaperUpdateComponent } from 'app/entities/research-paper/research-paper-update.component';
import { ResearchPaperService } from 'app/entities/research-paper/research-paper.service';
import { ResearchPaper } from 'app/shared/model/research-paper.model';

describe('Component Tests', () => {
    describe('ResearchPaper Management Update Component', () => {
        let comp: ResearchPaperUpdateComponent;
        let fixture: ComponentFixture<ResearchPaperUpdateComponent>;
        let service: ResearchPaperService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ResearchPaperUpdateComponent]
            })
                .overrideTemplate(ResearchPaperUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResearchPaperUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResearchPaperService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ResearchPaper(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.researchPaper = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ResearchPaper();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.researchPaper = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
