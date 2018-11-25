/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { CommonResourcesUpdateComponent } from 'app/entities/common-resources/common-resources-update.component';
import { CommonResourcesService } from 'app/entities/common-resources/common-resources.service';
import { CommonResources } from 'app/shared/model/common-resources.model';

describe('Component Tests', () => {
    describe('CommonResources Management Update Component', () => {
        let comp: CommonResourcesUpdateComponent;
        let fixture: ComponentFixture<CommonResourcesUpdateComponent>;
        let service: CommonResourcesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [CommonResourcesUpdateComponent]
            })
                .overrideTemplate(CommonResourcesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommonResourcesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommonResourcesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CommonResources(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commonResources = entity;
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
                    const entity = new CommonResources();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commonResources = entity;
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
