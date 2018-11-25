/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ExternalOnlineResourceDetailComponent } from 'app/entities/external-online-resource/external-online-resource-detail.component';
import { ExternalOnlineResource } from 'app/shared/model/external-online-resource.model';

describe('Component Tests', () => {
    describe('ExternalOnlineResource Management Detail Component', () => {
        let comp: ExternalOnlineResourceDetailComponent;
        let fixture: ComponentFixture<ExternalOnlineResourceDetailComponent>;
        const route = ({ data: of({ externalOnlineResource: new ExternalOnlineResource(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ExternalOnlineResourceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExternalOnlineResourceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExternalOnlineResourceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.externalOnlineResource).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
