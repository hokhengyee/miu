/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NewsAndEventService } from 'app/entities/news-and-event/news-and-event.service';
import { INewsAndEvent, NewsAndEvent } from 'app/shared/model/news-and-event.model';

describe('Service Tests', () => {
    describe('NewsAndEvent Service', () => {
        let injector: TestBed;
        let service: NewsAndEventService;
        let httpMock: HttpTestingController;
        let elemDefault: INewsAndEvent;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(NewsAndEventService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new NewsAndEvent(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        startDT: currentDate.format(DATE_TIME_FORMAT),
                        endDT: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a NewsAndEvent', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        startDT: currentDate.format(DATE_TIME_FORMAT),
                        endDT: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDT: currentDate,
                        endDT: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new NewsAndEvent(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a NewsAndEvent', async () => {
                const returnedFromService = Object.assign(
                    {
                        title: 'BBBBBB',
                        websiteLink: 'BBBBBB',
                        startDT: currentDate.format(DATE_TIME_FORMAT),
                        endDT: currentDate.format(DATE_TIME_FORMAT),
                        venue: 'BBBBBB',
                        eventDetail: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        startDT: currentDate,
                        endDT: currentDate
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

            it('should return a list of NewsAndEvent', async () => {
                const returnedFromService = Object.assign(
                    {
                        title: 'BBBBBB',
                        websiteLink: 'BBBBBB',
                        startDT: currentDate.format(DATE_TIME_FORMAT),
                        endDT: currentDate.format(DATE_TIME_FORMAT),
                        venue: 'BBBBBB',
                        eventDetail: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDT: currentDate,
                        endDT: currentDate
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

            it('should delete a NewsAndEvent', async () => {
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
