/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { PageViewLogUpdateComponent } from 'app/entities/page-view-log/page-view-log-update.component';
import { PageViewLogService } from 'app/entities/page-view-log/page-view-log.service';
import { PageViewLog } from 'app/shared/model/page-view-log.model';

describe('Component Tests', () => {
    describe('PageViewLog Management Update Component', () => {
        let comp: PageViewLogUpdateComponent;
        let fixture: ComponentFixture<PageViewLogUpdateComponent>;
        let service: PageViewLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [PageViewLogUpdateComponent]
            })
                .overrideTemplate(PageViewLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PageViewLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PageViewLogService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PageViewLog(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pageViewLog = entity;
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
                    const entity = new PageViewLog();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pageViewLog = entity;
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
