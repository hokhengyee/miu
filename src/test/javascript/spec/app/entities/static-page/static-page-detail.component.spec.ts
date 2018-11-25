/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StaticPageDetailComponent } from 'app/entities/static-page/static-page-detail.component';
import { StaticPage } from 'app/shared/model/static-page.model';

describe('Component Tests', () => {
    describe('StaticPage Management Detail Component', () => {
        let comp: StaticPageDetailComponent;
        let fixture: ComponentFixture<StaticPageDetailComponent>;
        const route = ({ data: of({ staticPage: new StaticPage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StaticPageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StaticPageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StaticPageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.staticPage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
