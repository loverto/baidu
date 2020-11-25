import { Moment } from 'moment';

export interface ILogisticsCenter {
  id?: number;
  regionName?: string;
  logisticsCenterName?: string;
  longitude?: string;
  dimension?: string;
  creationBy?: number;
  creationDate?: Moment;
  lastModifiedBy?: number;
  lastModifyTime?: Moment;
  lastModifiedDate?: Moment;
  available?: number;
}

export class LogisticsCenter implements ILogisticsCenter {
  constructor(
    public id?: number,
    public regionName?: string,
    public logisticsCenterName?: string,
    public longitude?: string,
    public dimension?: string,
    public creationBy?: number,
    public creationDate?: Moment,
    public lastModifiedBy?: number,
    public lastModifyTime?: Moment,
    public lastModifiedDate?: Moment,
    public available?: number
  ) {}
}
