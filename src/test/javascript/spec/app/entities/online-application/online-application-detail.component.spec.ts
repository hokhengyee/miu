/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { OnlineApplicationDetailComponent } from 'app/entities/online-application/online-application-detail.component';
import { OnlineApplication } from 'app/shared/model/online-application.model';

describe('Component Tests', () => {
    describe('OnlineApplication Management Detail Component', () => {
        let comp: OnlineApplicationDetailComponent;
        let fixture: ComponentFixture<OnlineApplicationDetailComponent>;
        const route = ({ data: of({ onlineApplication: new OnlineApplication(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [OnlineApplicationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OnlineApplicationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OnlineApplicationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.onlineApplication).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
