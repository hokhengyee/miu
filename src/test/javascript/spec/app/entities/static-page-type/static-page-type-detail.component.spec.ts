/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StaticPageTypeDetailComponent } from 'app/entities/static-page-type/static-page-type-detail.component';
import { StaticPageType } from 'app/shared/model/static-page-type.model';

describe('Component Tests', () => {
    describe('StaticPageType Management Detail Component', () => {
        let comp: StaticPageTypeDetailComponent;
        let fixture: ComponentFixture<StaticPageTypeDetailComponent>;
        const route = ({ data: of({ staticPageType: new StaticPageType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StaticPageTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StaticPageTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StaticPageTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.staticPageType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
