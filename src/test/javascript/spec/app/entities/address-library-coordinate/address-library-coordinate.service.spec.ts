import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AddressLibraryCoordinateService } from 'app/entities/address-library-coordinate/address-library-coordinate.service';
import { IAddressLibraryCoordinate, AddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';

describe('Service Tests', () => {
  describe('AddressLibraryCoordinate Service', () => {
    let injector: TestBed;
    let service: AddressLibraryCoordinateService;
    let httpMock: HttpTestingController;
    let elemDefault: IAddressLibraryCoordinate;
    let expectedResult: IAddressLibraryCoordinate | IAddressLibraryCoordinate[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AddressLibraryCoordinateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AddressLibraryCoordinate(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        currentDate,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createTime: currentDate.format(DATE_TIME_FORMAT),
            updateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AddressLibraryCoordinate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createTime: currentDate.format(DATE_TIME_FORMAT),
            updateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new AddressLibraryCoordinate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AddressLibraryCoordinate', () => {
        const returnedFromService = Object.assign(
          {
            addressId: 'BBBBBB',
            areaId: 'BBBBBB',
            code: 'BBBBBB',
            name: 'BBBBBB',
            zipCode: 'BBBBBB',
            parentCode: 'BBBBBB',
            addrLevel: 'BBBBBB',
            available: 1,
            seqNo: 1,
            createTime: currentDate.format(DATE_TIME_FORMAT),
            updateTime: currentDate.format(DATE_TIME_FORMAT),
            limitLine: 1,
            pinyinPrefix: 'BBBBBB',
            districtLatitudeLongitude: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AddressLibraryCoordinate', () => {
        const returnedFromService = Object.assign(
          {
            addressId: 'BBBBBB',
            areaId: 'BBBBBB',
            code: 'BBBBBB',
            name: 'BBBBBB',
            zipCode: 'BBBBBB',
            parentCode: 'BBBBBB',
            addrLevel: 'BBBBBB',
            available: 1,
            seqNo: 1,
            createTime: currentDate.format(DATE_TIME_FORMAT),
            updateTime: currentDate.format(DATE_TIME_FORMAT),
            limitLine: 1,
            pinyinPrefix: 'BBBBBB',
            districtLatitudeLongitude: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AddressLibraryCoordinate', () => {
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
