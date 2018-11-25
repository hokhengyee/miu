/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { SalutationUpdateComponent } from 'app/entities/salutation/salutation-update.component';
import { SalutationService } from 'app/entities/salutation/salutation.service';
import { Salutation } from 'app/shared/model/salutation.model';

describe('Component Tests', () => {
    describe('Salutation Management Update Component', () => {
        let comp: SalutationUpdateComponent;
        let fixture: ComponentFixture<SalutationUpdateComponent>;
        let service: SalutationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [SalutationUpdateComponent]
            })
                .overrideTemplate(SalutationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalutationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalutationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Salutation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salutation = entity;
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
                    const entity = new Salutation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salutation = entity;
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
