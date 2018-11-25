/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OnlineApplicationService } from 'app/entities/online-application/online-application.service';
import { IOnlineApplication, OnlineApplication } from 'app/shared/model/online-application.model';

describe('Service Tests', () => {
    describe('OnlineApplication Service', () => {
        let injector: TestBed;
        let service: OnlineApplicationService;
        let httpMock: HttpTestingController;
        let elemDefault: IOnlineApplication;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(OnlineApplicationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new OnlineApplication(
                0,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        registrationDatetime: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a OnlineApplication', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        registrationDatetime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate,
                        registrationDatetime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new OnlineApplication(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a OnlineApplication', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        telephone: 'BBBBBB',
                        email: 'BBBBBB',
                        city: 'BBBBBB',
                        state: 'BBBBBB',
                        country: 'BBBBBB',
                        postcode: 'BBBBBB',
                        registrationDatetime: currentDate.format(DATE_TIME_FORMAT),
                        surname: 'BBBBBB',
                        givenName: 'BBBBBB',
                        address: 'BBBBBB',
                        profilePhoto: 'BBBBBB',
                        academicCertificate: 'BBBBBB',
                        letterOfRecommendation: 'BBBBBB',
                        profileDocument: 'BBBBBB',
                        md5key: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate,
                        registrationDatetime: currentDate
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

            it('should return a list of OnlineApplication', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfBirth: currentDate.format(DATE_FORMAT),
                        telephone: 'BBBBBB',
                        email: 'BBBBBB',
                        city: 'BBBBBB',
                        state: 'BBBBBB',
                        country: 'BBBBBB',
                        postcode: 'BBBBBB',
                        registrationDatetime: currentDate.format(DATE_TIME_FORMAT),
                        surname: 'BBBBBB',
                        givenName: 'BBBBBB',
                        address: 'BBBBBB',
                        profilePhoto: 'BBBBBB',
                        academicCertificate: 'BBBBBB',
                        letterOfRecommendation: 'BBBBBB',
                        profileDocument: 'BBBBBB',
                        md5key: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfBirth: currentDate,
                        registrationDatetime: currentDate
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

            it('should delete a OnlineApplication', async () => {
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
