import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILogisticsCenter, LogisticsCenter } from 'app/shared/model/logistics-center.model';
import { LogisticsCenterService } from './logistics-center.service';

@Component({
  selector: 'jhi-logistics-center-update',
  templateUrl: './logistics-center-update.component.html',
})
export class LogisticsCenterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    regionName: [],
    logisticsCenterName: [],
    longitude: [],
    dimension: [],
    creationBy: [],
    creationDate: [],
    lastModifiedBy: [],
    lastModifyTime: [],
    lastModifiedDate: [],
    available: [],
  });

  constructor(
    protected logisticsCenterService: LogisticsCenterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logisticsCenter }) => {
      if (!logisticsCenter.id) {
        const today = moment().startOf('day');
        logisticsCenter.creationDate = today;
        logisticsCenter.lastModifyTime = today;
        logisticsCenter.lastModifiedDate = today;
      }

      this.updateForm(logisticsCenter);
    });
  }

  updateForm(logisticsCenter: ILogisticsCenter): void {
    this.editForm.patchValue({
      id: logisticsCenter.id,
      regionName: logisticsCenter.regionName,
      logisticsCenterName: logisticsCenter.logisticsCenterName,
      longitude: logisticsCenter.longitude,
      dimension: logisticsCenter.dimension,
      creationBy: logisticsCenter.creationBy,
      creationDate: logisticsCenter.creationDate ? logisticsCenter.creationDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: logisticsCenter.lastModifiedBy,
      lastModifyTime: logisticsCenter.lastModifyTime ? logisticsCenter.lastModifyTime.format(DATE_TIME_FORMAT) : null,
      lastModifiedDate: logisticsCenter.lastModifiedDate ? logisticsCenter.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      available: logisticsCenter.available,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logisticsCenter = this.createFromForm();
    if (logisticsCenter.id !== undefined) {
      this.subscribeToSaveResponse(this.logisticsCenterService.update(logisticsCenter));
    } else {
      this.subscribeToSaveResponse(this.logisticsCenterService.create(logisticsCenter));
    }
  }

  private createFromForm(): ILogisticsCenter {
    return {
      ...new LogisticsCenter(),
      id: this.editForm.get(['id'])!.value,
      regionName: this.editForm.get(['regionName'])!.value,
      logisticsCenterName: this.editForm.get(['logisticsCenterName'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      dimension: this.editForm.get(['dimension'])!.value,
      creationBy: this.editForm.get(['creationBy'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifyTime: this.editForm.get(['lastModifyTime'])!.value
        ? moment(this.editForm.get(['lastModifyTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      available: this.editForm.get(['available'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogisticsCenter>>): void {
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
