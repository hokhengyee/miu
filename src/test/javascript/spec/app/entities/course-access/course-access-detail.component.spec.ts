/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseAccessDetailComponent } from 'app/entities/course-access/course-access-detail.component';
import { CourseAccess } from 'app/shared/model/course-access.model';

describe('Component Tests', () => {
    describe('CourseAccess Management Detail Component', () => {
        let comp: CourseAccessDetailComponent;
        let fixture: ComponentFixture<CourseAccessDetailComponent>;
        const route = ({ data: of({ courseAccess: new CourseAccess(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseAccessDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourseAccessDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourseAccessDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courseAccess).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
