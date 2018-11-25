/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { RegistrationAcademicDetailsService } from 'app/entities/registration-academic-details/registration-academic-details.service';
import { IRegistrationAcademicDetails, RegistrationAcademicDetails } from 'app/shared/model/registration-academic-details.model';

describe('Service Tests', () => {
    describe('RegistrationAcademicDetails Service', () => {
        let injector: TestBed;
        let service: RegistrationAcademicDetailsService;
        let httpMock: HttpTestingController;
        let elemDefault: IRegistrationAcademicDetails;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RegistrationAcademicDetailsService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new RegistrationAcademicDetails(
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
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

            it('should create a RegistrationAcademicDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new RegistrationAcademicDetails(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a RegistrationAcademicDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        nameOfInstitution2: 'BBBBBB',
                        examPassed2: 'BBBBBB',
                        year2: 1,
                        grade2: 'BBBBBB',
                        nameOfInstitution3: 'BBBBBB',
                        examPassed3: 'BBBBBB',
                        year3: 1,
                        grade3: 'BBBBBB',
                        nameOfInstitution4: 'BBBBBB',
                        examPassed4: 'BBBBBB',
                        year4: 1,
                        grade4: 'BBBBBB',
                        md5key: 'BBBBBB',
                        nameOfInstitution1: 'BBBBBB',
                        examPassed1: 'BBBBBB',
                        year1: 1,
                        grade1: 'BBBBBB'
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

            it('should return a list of RegistrationAcademicDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        nameOfInstitution2: 'BBBBBB',
                        examPassed2: 'BBBBBB',
                        year2: 1,
                        grade2: 'BBBBBB',
                        nameOfInstitution3: 'BBBBBB',
                        examPassed3: 'BBBBBB',
                        year3: 1,
                        grade3: 'BBBBBB',
                        nameOfInstitution4: 'BBBBBB',
                        examPassed4: 'BBBBBB',
                        year4: 1,
                        grade4: 'BBBBBB',
                        md5key: 'BBBBBB',
                        nameOfInstitution1: 'BBBBBB',
                        examPassed1: 'BBBBBB',
                        year1: 1,
                        grade1: 'BBBBBB'
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

            it('should delete a RegistrationAcademicDetails', async () => {
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
