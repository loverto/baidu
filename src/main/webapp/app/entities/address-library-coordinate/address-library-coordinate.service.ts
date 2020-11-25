import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';

type EntityResponseType = HttpResponse<IAddressLibraryCoordinate>;
type EntityArrayResponseType = HttpResponse<IAddressLibraryCoordinate[]>;

@Injectable({ providedIn: 'root' })
export class AddressLibraryCoordinateService {
  public resourceUrl = SERVER_API_URL + 'api/address-library-coordinates';

  constructor(protected http: HttpClient) {}

  create(addressLibraryCoordinate: IAddressLibraryCoordinate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addressLibraryCoordinate);
    return this.http
      .post<IAddressLibraryCoordinate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(addressLibraryCoordinate: IAddressLibraryCoordinate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addressLibraryCoordinate);
    return this.http
      .put<IAddressLibraryCoordinate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAddressLibraryCoordinate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAddressLibraryCoordinate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(addressLibraryCoordinate: IAddressLibraryCoordinate): IAddressLibraryCoordinate {
    const copy: IAddressLibraryCoordinate = Object.assign({}, addressLibraryCoordinate, {
      createTime:
        addressLibraryCoordinate.createTime && addressLibraryCoordinate.createTime.isValid()
          ? addressLibraryCoordinate.createTime.toJSON()
          : undefined,
      updateTime:
        addressLibraryCoordinate.updateTime && addressLibraryCoordinate.updateTime.isValid()
          ? addressLibraryCoordinate.updateTime.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createTime = res.body.createTime ? moment(res.body.createTime) : undefined;
      res.body.updateTime = res.body.updateTime ? moment(res.body.updateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((addressLibraryCoordinate: IAddressLibraryCoordinate) => {
        addressLibraryCoordinate.createTime = addressLibraryCoordinate.createTime ? moment(addressLibraryCoordinate.createTime) : undefined;
        addressLibraryCoordinate.updateTime = addressLibraryCoordinate.updateTime ? moment(addressLibraryCoordinate.updateTime) : undefined;
      });
    }
    return res;
  }
}
