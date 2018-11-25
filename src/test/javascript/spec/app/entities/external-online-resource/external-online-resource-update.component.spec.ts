/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { ExternalOnlineResourceUpdateComponent } from 'app/entities/external-online-resource/external-online-resource-update.component';
import { ExternalOnlineResourceService } from 'app/entities/external-online-resource/external-online-resource.service';
import { ExternalOnlineResource } from 'app/shared/model/external-online-resource.model';

describe('Component Tests', () => {
    describe('ExternalOnlineResource Management Update Component', () => {
        let comp: ExternalOnlineResourceUpdateComponent;
        let fixture: ComponentFixture<ExternalOnlineResourceUpdateComponent>;
        let service: ExternalOnlineResourceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [ExternalOnlineResourceUpdateComponent]
            })
                .overrideTemplate(ExternalOnlineResourceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExternalOnlineResourceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExternalOnlineResourceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ExternalOnlineResource(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.externalOnlineResource = entity;
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
                    const entity = new ExternalOnlineResource();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.externalOnlineResource = entity;
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
