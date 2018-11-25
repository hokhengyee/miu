/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MiuTestModule } from '../../../test.module';
import { StudentProfileUpdateComponent } from 'app/entities/student-profile/student-profile-update.component';
import { StudentProfileService } from 'app/entities/student-profile/student-profile.service';
import { StudentProfile } from 'app/shared/model/student-profile.model';

describe('Component Tests', () => {
    describe('StudentProfile Management Update Component', () => {
        let comp: StudentProfileUpdateComponent;
        let fixture: ComponentFixture<StudentProfileUpdateComponent>;
        let service: StudentProfileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MiuTestModule],
                declarations: [StudentProfileUpdateComponent]
            })
                .overrideTemplate(StudentProfileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentProfileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentProfileService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StudentProfile(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentProfile = entity;
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
                    const entity = new StudentProfile();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentProfile = entity;
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
