import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogisticsCenter } from 'app/shared/model/logistics-center.model';
import { LogisticsCenterService } from './logistics-center.service';

@Component({
  templateUrl: './logistics-center-delete-dialog.component.html',
})
export class LogisticsCenterDeleteDialogComponent {
  logisticsCenter?: ILogisticsCenter;

  constructor(
    protected logisticsCenterService: LogisticsCenterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logisticsCenterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logisticsCenterListModification');
      this.activeModal.close();
    });
  }
}
