/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { NewsAndEventUpdateComponent } from 'app/entities/news-and-event/news-and-event-update.component';
import { NewsAndEventService } from 'app/entities/news-and-event/news-and-event.service';
import { NewsAndEvent } from 'app/shared/model/news-and-event.model';

describe('Component Tests', () => {
    describe('NewsAndEvent Management Update Component', () => {
        let comp: NewsAndEventUpdateComponent;
        let fixture: ComponentFixture<NewsAndEventUpdateComponent>;
        let service: NewsAndEventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [NewsAndEventUpdateComponent]
            })
                .overrideTemplate(NewsAndEventUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NewsAndEventUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NewsAndEventService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new NewsAndEvent(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.newsAndEvent = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new NewsAndEvent();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.newsAndEvent = entity;
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
