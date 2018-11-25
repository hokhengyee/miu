/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { PageViewLogDetailComponent } from 'app/entities/page-view-log/page-view-log-detail.component';
import { PageViewLog } from 'app/shared/model/page-view-log.model';

describe('Component Tests', () => {
    describe('PageViewLog Management Detail Component', () => {
        let comp: PageViewLogDetailComponent;
        let fixture: ComponentFixture<PageViewLogDetailComponent>;
        const route = ({ data: of({ pageViewLog: new PageViewLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [PageViewLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PageViewLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PageViewLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pageViewLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
