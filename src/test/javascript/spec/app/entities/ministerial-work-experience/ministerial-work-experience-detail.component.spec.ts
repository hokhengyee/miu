/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { MinisterialWorkExperienceDetailComponent } from 'app/entities/ministerial-work-experience/ministerial-work-experience-detail.component';
import { MinisterialWorkExperience } from 'app/shared/model/ministerial-work-experience.model';

describe('Component Tests', () => {
    describe('MinisterialWorkExperience Management Detail Component', () => {
        let comp: MinisterialWorkExperienceDetailComponent;
        let fixture: ComponentFixture<MinisterialWorkExperienceDetailComponent>;
        const route = ({ data: of({ ministerialWorkExperience: new MinisterialWorkExperience(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [MinisterialWorkExperienceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MinisterialWorkExperienceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MinisterialWorkExperienceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ministerialWorkExperience).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
