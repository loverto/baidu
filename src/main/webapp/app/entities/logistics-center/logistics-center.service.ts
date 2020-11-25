import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILogisticsCenter } from 'app/shared/model/logistics-center.model';

type EntityResponseType = HttpResponse<ILogisticsCenter>;
type EntityArrayResponseType = HttpResponse<ILogisticsCenter[]>;

@Injectable({ providedIn: 'root' })
export class LogisticsCenterService {
  public resourceUrl = SERVER_API_URL + 'api/logistics-centers';

  constructor(protected http: HttpClient) {}

  create(logisticsCenter: ILogisticsCenter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logisticsCenter);
    return this.http
      .post<ILogisticsCenter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logisticsCenter: ILogisticsCenter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logisticsCenter);
    return this.http
      .put<ILogisticsCenter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogisticsCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogisticsCenter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(logisticsCenter: ILogisticsCenter): ILogisticsCenter {
    const copy: ILogisticsCenter = Object.assign({}, logisticsCenter, {
      creationDate:
        logisticsCenter.creationDate && logisticsCenter.creationDate.isValid() ? logisticsCenter.creationDate.toJSON() : undefined,
      lastModifyTime:
        logisticsCenter.lastModifyTime && logisticsCenter.lastModifyTime.isValid() ? logisticsCenter.lastModifyTime.toJSON() : undefined,
      lastModifiedDate:
        logisticsCenter.lastModifiedDate && logisticsCenter.lastModifiedDate.isValid()
          ? logisticsCenter.lastModifiedDate.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.lastModifyTime = res.body.lastModifyTime ? moment(res.body.lastModifyTime) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logisticsCenter: ILogisticsCenter) => {
        logisticsCenter.creationDate = logisticsCenter.creationDate ? moment(logisticsCenter.creationDate) : undefined;
        logisticsCenter.lastModifyTime = logisticsCenter.lastModifyTime ? moment(logisticsCenter.lastModifyTime) : undefined;
        logisticsCenter.lastModifiedDate = logisticsCenter.lastModifiedDate ? moment(logisticsCenter.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
