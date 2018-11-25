/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { NewsAndEventDetailComponent } from 'app/entities/news-and-event/news-and-event-detail.component';
import { NewsAndEvent } from 'app/shared/model/news-and-event.model';

describe('Component Tests', () => {
    describe('NewsAndEvent Management Detail Component', () => {
        let comp: NewsAndEventDetailComponent;
        let fixture: ComponentFixture<NewsAndEventDetailComponent>;
        const route = ({ data: of({ newsAndEvent: new NewsAndEvent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [NewsAndEventDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NewsAndEventDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NewsAndEventDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.newsAndEvent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
