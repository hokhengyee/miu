/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RecordOfCertificateService } from 'app/entities/record-of-certificate/record-of-certificate.service';
import { IRecordOfCertificate, RecordOfCertificate } from 'app/shared/model/record-of-certificate.model';

describe('Service Tests', () => {
    describe('RecordOfCertificate Service', () => {
        let injector: TestBed;
        let service: RecordOfCertificateService;
        let httpMock: HttpTestingController;
        let elemDefault: IRecordOfCertificate;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RecordOfCertificateService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new RecordOfCertificate(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        certDate: currentDate.format(DATE_FORMAT)
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

            it('should create a RecordOfCertificate', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        certDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        certDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new RecordOfCertificate(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a RecordOfCertificate', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        degree: 'BBBBBB',
                        studentNo: 'BBBBBB',
                        certNumber: 'BBBBBB',
                        certScanFile: 'BBBBBB',
                        certDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        certDate: currentDate
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

            it('should return a list of RecordOfCertificate', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        degree: 'BBBBBB',
                        studentNo: 'BBBBBB',
                        certNumber: 'BBBBBB',
                        certScanFile: 'BBBBBB',
                        certDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        certDate: currentDate
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

            it('should delete a RecordOfCertificate', async () => {
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
