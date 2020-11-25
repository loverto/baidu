import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BaiduSharedModule } from 'app/shared/shared.module';
import { AddressLibraryCoordinateComponent } from './address-library-coordinate.component';
import { AddressLibraryCoordinateDetailComponent } from './address-library-coordinate-detail.component';
import { AddressLibraryCoordinateUpdateComponent } from './address-library-coordinate-update.component';
import { AddressLibraryCoordinateDeleteDialogComponent } from './address-library-coordinate-delete-dialog.component';
import { addressLibraryCoordinateRoute } from './address-library-coordinate.route';

@NgModule({
  imports: [BaiduSharedModule, RouterModule.forChild(addressLibraryCoordinateRoute)],
  declarations: [
    AddressLibraryCoordinateComponent,
    AddressLibraryCoordinateDetailComponent,
    AddressLibraryCoordinateUpdateComponent,
    AddressLibraryCoordinateDeleteDialogComponent,
  ],
  entryComponents: [AddressLibraryCoordinateDeleteDialogComponent],
})
export class BaiduAddressLibraryCoordinateModule {}
