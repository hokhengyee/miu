/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StaticPageUpdateComponent } from 'app/entities/static-page/static-page-update.component';
import { StaticPageService } from 'app/entities/static-page/static-page.service';
import { StaticPage } from 'app/shared/model/static-page.model';

describe('Component Tests', () => {
    describe('StaticPage Management Update Component', () => {
        let comp: StaticPageUpdateComponent;
        let fixture: ComponentFixture<StaticPageUpdateComponent>;
        let service: StaticPageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StaticPageUpdateComponent]
            })
                .overrideTemplate(StaticPageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StaticPageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StaticPageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StaticPage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.staticPage = entity;
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
                    const entity = new StaticPage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.staticPage = entity;
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
