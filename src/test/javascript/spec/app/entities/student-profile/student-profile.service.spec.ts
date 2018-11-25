/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentProfileService } from 'app/entities/student-profile/student-profile.service';
import { IStudentProfile, StudentProfile } from 'app/shared/model/student-profile.model';

describe('Service Tests', () => {
    describe('StudentProfile Service', () => {
        let injector: TestBed;
        let service: StudentProfileService;
        let httpMock: HttpTestingController;
        let elemDefault: IStudentProfile;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StudentProfileService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StudentProfile(
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        applicationDate: currentDate.format(DATE_FORMAT),
                        commencementDate: currentDate.format(DATE_FORMAT),
                        completionDate: currentDate.format(DATE_FORMAT),
                        extendedCompletionDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a StudentProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        applicationDate: currentDate.format(DATE_FORMAT),
                        commencementDate: currentDate.format(DATE_FORMAT),
                        completionDate: currentDate.format(DATE_FORMAT),
                        extendedCompletionDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate,
                        applicationDate: currentDate,
                        commencementDate: currentDate,
                        completionDate: currentDate,
                        extendedCompletionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StudentProfile(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StudentProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        studentId: 'BBBBBB',
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        phone: 'BBBBBB',
                        applicationDate: currentDate.format(DATE_FORMAT),
                        commencementDate: currentDate.format(DATE_FORMAT),
                        completionDate: currentDate.format(DATE_FORMAT),
                        mailingAddress: 'BBBBBB',
                        profilePhoto: 'BBBBBB',
                        extendedCompletionDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate,
                        applicationDate: currentDate,
                        commencementDate: currentDate,
                        completionDate: currentDate,
                        extendedCompletionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of StudentProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        studentId: 'BBBBBB',
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        phone: 'BBBBBB',
                        applicationDate: currentDate.format(DATE_FORMAT),
                        commencementDate: currentDate.format(DATE_FORMAT),
                        completionDate: currentDate.format(DATE_FORMAT),
                        mailingAddress: 'BBBBBB',
                        profilePhoto: 'BBBBBB',
                        extendedCompletionDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate,
                        applicationDate: currentDate,
                        commencementDate: currentDate,
                        completionDate: currentDate,
                        extendedCompletionDate: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a StudentProfile', async () => {
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
