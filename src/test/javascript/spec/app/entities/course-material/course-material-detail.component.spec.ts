/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseMaterialDetailComponent } from 'app/entities/course-material/course-material-detail.component';
import { CourseMaterial } from 'app/shared/model/course-material.model';

describe('Component Tests', () => {
    describe('CourseMaterial Management Detail Component', () => {
        let comp: CourseMaterialDetailComponent;
        let fixture: ComponentFixture<CourseMaterialDetailComponent>;
        const route = ({ data: of({ courseMaterial: new CourseMaterial(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseMaterialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourseMaterialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourseMaterialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courseMaterial).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
