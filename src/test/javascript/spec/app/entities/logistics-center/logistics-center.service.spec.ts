import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LogisticsCenterService } from 'app/entities/logistics-center/logistics-center.service';
import { ILogisticsCenter, LogisticsCenter } from 'app/shared/model/logistics-center.model';

describe('Service Tests', () => {
  describe('LogisticsCenter Service', () => {
    let injector: TestBed;
    let service: LogisticsCenterService;
    let httpMock: HttpTestingController;
    let elemDefault: ILogisticsCenter;
    let expectedResult: ILogisticsCenter | ILogisticsCenter[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LogisticsCenterService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LogisticsCenter(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, 0, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifyTime: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LogisticsCenter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifyTime: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            lastModifyTime: currentDate,
            lastModifiedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new LogisticsCenter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LogisticsCenter', () => {
        const returnedFromService = Object.assign(
          {
            regionName: 'BBBBBB',
            logisticsCenterName: 'BBBBBB',
            longitude: 'BBBBBB',
            dimension: 'BBBBBB',
            creationBy: 1,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 1,
            lastModifyTime: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            available: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            lastModifyTime: currentDate,
            lastModifiedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LogisticsCenter', () => {
        const returnedFromService = Object.assign(
          {
            regionName: 'BBBBBB',
            logisticsCenterName: 'BBBBBB',
            longitude: 'BBBBBB',
            dimension: 'BBBBBB',
            creationBy: 1,
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 1,
            lastModifyTime: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            available: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            lastModifyTime: currentDate,
            lastModifiedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LogisticsCenter', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
