/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { AdjunctFacultyDetailComponent } from 'app/entities/adjunct-faculty/adjunct-faculty-detail.component';
import { AdjunctFaculty } from 'app/shared/model/adjunct-faculty.model';

describe('Component Tests', () => {
    describe('AdjunctFaculty Management Detail Component', () => {
        let comp: AdjunctFacultyDetailComponent;
        let fixture: ComponentFixture<AdjunctFacultyDetailComponent>;
        const route = ({ data: of({ adjunctFaculty: new AdjunctFaculty(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [AdjunctFacultyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdjunctFacultyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdjunctFacultyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adjunctFaculty).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
