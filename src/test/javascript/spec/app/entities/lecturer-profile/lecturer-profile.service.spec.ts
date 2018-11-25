/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { LecturerProfileService } from 'app/entities/lecturer-profile/lecturer-profile.service';
import { ILecturerProfile, LecturerProfile } from 'app/shared/model/lecturer-profile.model';

describe('Service Tests', () => {
    describe('LecturerProfile Service', () => {
        let injector: TestBed;
        let service: LecturerProfileService;
        let httpMock: HttpTestingController;
        let elemDefault: ILecturerProfile;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LecturerProfileService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new LecturerProfile(
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'image/png',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a LecturerProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new LecturerProfile(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a LecturerProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        otherTitles: 'BBBBBB',
                        age: 1,
                        ordination: 'BBBBBB',
                        academicHistory: 'BBBBBB',
                        professionalHistory: 'BBBBBB',
                        pastAndCurrentMinistry: 'BBBBBB',
                        publications: 'BBBBBB',
                        familyDetails: 'BBBBBB',
                        reference: 'BBBBBB',
                        profilePhoto: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of LecturerProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        otherTitles: 'BBBBBB',
                        age: 1,
                        ordination: 'BBBBBB',
                        academicHistory: 'BBBBBB',
                        professionalHistory: 'BBBBBB',
                        pastAndCurrentMinistry: 'BBBBBB',
                        publications: 'BBBBBB',
                        familyDetails: 'BBBBBB',
                        reference: 'BBBBBB',
                        profilePhoto: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a LecturerProfile', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
