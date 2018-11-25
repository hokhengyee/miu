/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ResearchPaperDetailComponent } from 'app/entities/research-paper/research-paper-detail.component';
import { ResearchPaper } from 'app/shared/model/research-paper.model';

describe('Component Tests', () => {
    describe('ResearchPaper Management Detail Component', () => {
        let comp: ResearchPaperDetailComponent;
        let fixture: ComponentFixture<ResearchPaperDetailComponent>;
        const route = ({ data: of({ researchPaper: new ResearchPaper(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ResearchPaperDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResearchPaperDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResearchPaperDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.researchPaper).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
