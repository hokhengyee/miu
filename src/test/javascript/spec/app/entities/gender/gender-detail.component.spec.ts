/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { GenderDetailComponent } from 'app/entities/gender/gender-detail.component';
import { Gender } from 'app/shared/model/gender.model';

describe('Component Tests', () => {
    describe('Gender Management Detail Component', () => {
        let comp: GenderDetailComponent;
        let fixture: ComponentFixture<GenderDetailComponent>;
        const route = ({ data: of({ gender: new Gender(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [GenderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GenderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GenderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gender).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
