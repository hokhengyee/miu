/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CourseModuleDetailComponent } from 'app/entities/course-module/course-module-detail.component';
import { CourseModule } from 'app/shared/model/course-module.model';

describe('Component Tests', () => {
    describe('CourseModule Management Detail Component', () => {
        let comp: CourseModuleDetailComponent;
        let fixture: ComponentFixture<CourseModuleDetailComponent>;
        const route = ({ data: of({ courseModule: new CourseModule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CourseModuleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourseModuleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourseModuleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courseModule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
