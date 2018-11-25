/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { EntryQualificationUpdateComponent } from 'app/entities/entry-qualification/entry-qualification-update.component';
import { EntryQualificationService } from 'app/entities/entry-qualification/entry-qualification.service';
import { EntryQualification } from 'app/shared/model/entry-qualification.model';

describe('Component Tests', () => {
    describe('EntryQualification Management Update Component', () => {
        let comp: EntryQualificationUpdateComponent;
        let fixture: ComponentFixture<EntryQualificationUpdateComponent>;
        let service: EntryQualificationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [EntryQualificationUpdateComponent]
            })
                .overrideTemplate(EntryQualificationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EntryQualificationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntryQualificationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EntryQualification(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.entryQualification = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EntryQualification();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.entryQualification = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
