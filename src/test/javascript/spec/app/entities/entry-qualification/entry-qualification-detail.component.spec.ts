/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { EntryQualificationDetailComponent } from 'app/entities/entry-qualification/entry-qualification-detail.component';
import { EntryQualification } from 'app/shared/model/entry-qualification.model';

describe('Component Tests', () => {
    describe('EntryQualification Management Detail Component', () => {
        let comp: EntryQualificationDetailComponent;
        let fixture: ComponentFixture<EntryQualificationDetailComponent>;
        const route = ({ data: of({ entryQualification: new EntryQualification(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [EntryQualificationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EntryQualificationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntryQualificationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.entryQualification).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
