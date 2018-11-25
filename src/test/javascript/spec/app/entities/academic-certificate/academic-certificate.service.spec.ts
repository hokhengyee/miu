/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { AcademicCertificateService } from 'app/entities/academic-certificate/academic-certificate.service';
import { IAcademicCertificate, AcademicCertificate } from 'app/shared/model/academic-certificate.model';

describe('Service Tests', () => {
    describe('AcademicCertificate Service', () => {
        let injector: TestBed;
        let service: AcademicCertificateService;
        let httpMock: HttpTestingController;
        let elemDefault: IAcademicCertificate;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AcademicCertificateService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new AcademicCertificate(0, 'AAAAAAA', 'image/png', 'AAAAAAA', 'image/png', 'AAAAAAA', 'image/png', 'AAAAAAA');
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

            it('should create a AcademicCertificate', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new AcademicCertificate(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a AcademicCertificate', async () => {
                const returnedFromService = Object.assign(
                    {
                        md5Key: 'BBBBBB',
                        academicCertificate1: 'BBBBBB',
                        academicCertificate2: 'BBBBBB',
                        academicCertificate3: 'BBBBBB'
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

            it('should return a list of AcademicCertificate', async () => {
                const returnedFromService = Object.assign(
                    {
                        md5Key: 'BBBBBB',
                        academicCertificate1: 'BBBBBB',
                        academicCertificate2: 'BBBBBB',
                        academicCertificate3: 'BBBBBB'
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

            it('should delete a AcademicCertificate', async () => {
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
