import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BaiduSharedModule } from 'app/shared/shared.module';
import { LogisticsCenterComponent } from './logistics-center.component';
import { LogisticsCenterDetailComponent } from './logistics-center-detail.component';
import { LogisticsCenterUpdateComponent } from './logistics-center-update.component';
import { LogisticsCenterDeleteDialogComponent } from './logistics-center-delete-dialog.component';
import { logisticsCenterRoute } from './logistics-center.route';

@NgModule({
  imports: [BaiduSharedModule, RouterModule.forChild(logisticsCenterRoute)],
  declarations: [
    LogisticsCenterComponent,
    LogisticsCenterDetailComponent,
    LogisticsCenterUpdateComponent,
    LogisticsCenterDeleteDialogComponent,
  ],
  entryComponents: [LogisticsCenterDeleteDialogComponent],
})
export class BaiduLogisticsCenterModule {}
