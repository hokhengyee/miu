/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { LecturerProfileUpdateComponent } from 'app/entities/lecturer-profile/lecturer-profile-update.component';
import { LecturerProfileService } from 'app/entities/lecturer-profile/lecturer-profile.service';
import { LecturerProfile } from 'app/shared/model/lecturer-profile.model';

describe('Component Tests', () => {
    describe('LecturerProfile Management Update Component', () => {
        let comp: LecturerProfileUpdateComponent;
        let fixture: ComponentFixture<LecturerProfileUpdateComponent>;
        let service: LecturerProfileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [LecturerProfileUpdateComponent]
            })
                .overrideTemplate(LecturerProfileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LecturerProfileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturerProfileService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LecturerProfile(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lecturerProfile = entity;
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
                    const entity = new LecturerProfile();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lecturerProfile = entity;
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
