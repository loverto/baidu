import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAddressLibraryCoordinate, AddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';
import { AddressLibraryCoordinateService } from './address-library-coordinate.service';

@Component({
  selector: 'jhi-address-library-coordinate-update',
  templateUrl: './address-library-coordinate-update.component.html',
})
export class AddressLibraryCoordinateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    addressId: [],
    areaId: [],
    code: [],
    name: [],
    zipCode: [],
    parentCode: [],
    addrLevel: [],
    available: [],
    seqNo: [],
    createTime: [],
    updateTime: [],
    limitLine: [],
    pinyinPrefix: [],
    districtLatitudeLongitude: [],
  });

  constructor(
    protected addressLibraryCoordinateService: AddressLibraryCoordinateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addressLibraryCoordinate }) => {
      if (!addressLibraryCoordinate.id) {
        const today = moment().startOf('day');
        addressLibraryCoordinate.createTime = today;
        addressLibraryCoordinate.updateTime = today;
      }

      this.updateForm(addressLibraryCoordinate);
    });
  }

  updateForm(addressLibraryCoordinate: IAddressLibraryCoordinate): void {
    this.editForm.patchValue({
      id: addressLibraryCoordinate.id,
      addressId: addressLibraryCoordinate.addressId,
      areaId: addressLibraryCoordinate.areaId,
      code: addressLibraryCoordinate.code,
      name: addressLibraryCoordinate.name,
      zipCode: addressLibraryCoordinate.zipCode,
      parentCode: addressLibraryCoordinate.parentCode,
      addrLevel: addressLibraryCoordinate.addrLevel,
      available: addressLibraryCoordinate.available,
      seqNo: addressLibraryCoordinate.seqNo,
      createTime: addressLibraryCoordinate.createTime ? addressLibraryCoordinate.createTime.format(DATE_TIME_FORMAT) : null,
      updateTime: addressLibraryCoordinate.updateTime ? addressLibraryCoordinate.updateTime.format(DATE_TIME_FORMAT) : null,
      limitLine: addressLibraryCoordinate.limitLine,
      pinyinPrefix: addressLibraryCoordinate.pinyinPrefix,
      districtLatitudeLongitude: addressLibraryCoordinate.districtLatitudeLongitude,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const addressLibraryCoordinate = this.createFromForm();
    if (addressLibraryCoordinate.id !== undefined) {
      this.subscribeToSaveResponse(this.addressLibraryCoordinateService.update(addressLibraryCoordinate));
    } else {
      this.subscribeToSaveResponse(this.addressLibraryCoordinateService.create(addressLibraryCoordinate));
    }
  }

  private createFromForm(): IAddressLibraryCoordinate {
    return {
      ...new AddressLibraryCoordinate(),
      id: this.editForm.get(['id'])!.value,
      addressId: this.editForm.get(['addressId'])!.value,
      areaId: this.editForm.get(['areaId'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      parentCode: this.editForm.get(['parentCode'])!.value,
      addrLevel: this.editForm.get(['addrLevel'])!.value,
      available: this.editForm.get(['available'])!.value,
      seqNo: this.editForm.get(['seqNo'])!.value,
      createTime: this.editForm.get(['createTime'])!.value ? moment(this.editForm.get(['createTime'])!.value, DATE_TIME_FORMAT) : undefined,
      updateTime: this.editForm.get(['updateTime'])!.value ? moment(this.editForm.get(['updateTime'])!.value, DATE_TIME_FORMAT) : undefined,
      limitLine: this.editForm.get(['limitLine'])!.value,
      pinyinPrefix: this.editForm.get(['pinyinPrefix'])!.value,
      districtLatitudeLongitude: this.editForm.get(['districtLatitudeLongitude'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressLibraryCoordinate>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
