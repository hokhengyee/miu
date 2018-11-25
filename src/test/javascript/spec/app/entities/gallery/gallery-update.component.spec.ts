/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { GalleryUpdateComponent } from 'app/entities/gallery/gallery-update.component';
import { GalleryService } from 'app/entities/gallery/gallery.service';
import { Gallery } from 'app/shared/model/gallery.model';

describe('Component Tests', () => {
    describe('Gallery Management Update Component', () => {
        let comp: GalleryUpdateComponent;
        let fixture: ComponentFixture<GalleryUpdateComponent>;
        let service: GalleryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [GalleryUpdateComponent]
            })
                .overrideTemplate(GalleryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GalleryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Gallery(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.gallery = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Gallery();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.gallery = entity;
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
