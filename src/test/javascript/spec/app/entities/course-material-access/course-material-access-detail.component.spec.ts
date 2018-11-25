/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseMaterialAccessDetailComponent } from 'app/entities/course-material-access/course-material-access-detail.component';
import { CourseMaterialAccess } from 'app/shared/model/course-material-access.model';

describe('Component Tests', () => {
    describe('CourseMaterialAccess Management Detail Component', () => {
        let comp: CourseMaterialAccessDetailComponent;
        let fixture: ComponentFixture<CourseMaterialAccessDetailComponent>;
        const route = ({ data: of({ courseMaterialAccess: new CourseMaterialAccess(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseMaterialAccessDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourseMaterialAccessDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourseMaterialAccessDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courseMaterialAccess).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
