/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { OnlineApplicationUpdateComponent } from 'app/entities/online-application/online-application-update.component';
import { OnlineApplicationService } from 'app/entities/online-application/online-application.service';
import { OnlineApplication } from 'app/shared/model/online-application.model';

describe('Component Tests', () => {
    describe('OnlineApplication Management Update Component', () => {
        let comp: OnlineApplicationUpdateComponent;
        let fixture: ComponentFixture<OnlineApplicationUpdateComponent>;
        let service: OnlineApplicationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [OnlineApplicationUpdateComponent]
            })
                .overrideTemplate(OnlineApplicationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OnlineApplicationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OnlineApplicationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OnlineApplication(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.onlineApplication = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OnlineApplication();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.onlineApplication = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
