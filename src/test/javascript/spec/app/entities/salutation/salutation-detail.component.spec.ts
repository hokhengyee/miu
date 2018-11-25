/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { SalutationDetailComponent } from 'app/entities/salutation/salutation-detail.component';
import { Salutation } from 'app/shared/model/salutation.model';

describe('Component Tests', () => {
    describe('Salutation Management Detail Component', () => {
        let comp: SalutationDetailComponent;
        let fixture: ComponentFixture<SalutationDetailComponent>;
        const route = ({ data: of({ salutation: new Salutation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [SalutationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SalutationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalutationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salutation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
