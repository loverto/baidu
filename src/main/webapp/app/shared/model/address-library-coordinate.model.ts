import { Moment } from 'moment';

export interface IAddressLibraryCoordinate {
  id?: number;
  addressId?: string;
  areaId?: string;
  code?: string;
  name?: string;
  zipCode?: string;
  parentCode?: string;
  addrLevel?: string;
  available?: number;
  seqNo?: number;
  createTime?: Moment;
  updateTime?: Moment;
  limitLine?: number;
  pinyinPrefix?: string;
  districtLatitudeLongitude?: string;
}

export class AddressLibraryCoordinate implements IAddressLibraryCoordinate {
  constructor(
    public id?: number,
    public addressId?: string,
    public areaId?: string,
    public code?: string,
    public name?: string,
    public zipCode?: string,
    public parentCode?: string,
    public addrLevel?: string,
    public available?: number,
    public seqNo?: number,
    public createTime?: Moment,
    public updateTime?: Moment,
    public limitLine?: number,
    public pinyinPrefix?: string,
    public districtLatitudeLongitude?: string
  ) {}
}
