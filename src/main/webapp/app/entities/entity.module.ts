import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'logistics-center',
        loadChildren: () => import('./logistics-center/logistics-center.module').then(m => m.BaiduLogisticsCenterModule),
      },
      {
        path: 'address-library-coordinate',
        loadChildren: () =>
          import('./address-library-coordinate/address-library-coordinate.module').then(m => m.BaiduAddressLibraryCoordinateModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BaiduEntityModule {}
