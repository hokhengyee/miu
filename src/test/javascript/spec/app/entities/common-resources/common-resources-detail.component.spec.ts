/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CommonResourcesDetailComponent } from 'app/entities/common-resources/common-resources-detail.component';
import { CommonResources } from 'app/shared/model/common-resources.model';

describe('Component Tests', () => {
    describe('CommonResources Management Detail Component', () => {
        let comp: CommonResourcesDetailComponent;
        let fixture: ComponentFixture<CommonResourcesDetailComponent>;
        const route = ({ data: of({ commonResources: new CommonResources(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CommonResourcesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommonResourcesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommonResourcesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commonResources).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
