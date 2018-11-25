/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StaticPageTypeUpdateComponent } from 'app/entities/static-page-type/static-page-type-update.component';
import { StaticPageTypeService } from 'app/entities/static-page-type/static-page-type.service';
import { StaticPageType } from 'app/shared/model/static-page-type.model';

describe('Component Tests', () => {
    describe('StaticPageType Management Update Component', () => {
        let comp: StaticPageTypeUpdateComponent;
        let fixture: ComponentFixture<StaticPageTypeUpdateComponent>;
        let service: StaticPageTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StaticPageTypeUpdateComponent]
            })
                .overrideTemplate(StaticPageTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StaticPageTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StaticPageTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StaticPageType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.staticPageType = entity;
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
                    const entity = new StaticPageType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.staticPageType = entity;
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
