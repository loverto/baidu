import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAddressLibraryCoordinate, AddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';
import { AddressLibraryCoordinateService } from './address-library-coordinate.service';
import { AddressLibraryCoordinateComponent } from './address-library-coordinate.component';
import { AddressLibraryCoordinateDetailComponent } from './address-library-coordinate-detail.component';
import { AddressLibraryCoordinateUpdateComponent } from './address-library-coordinate-update.component';

@Injectable({ providedIn: 'root' })
export class AddressLibraryCoordinateResolve implements Resolve<IAddressLibraryCoordinate> {
  constructor(private service: AddressLibraryCoordinateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddressLibraryCoordinate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((addressLibraryCoordinate: HttpResponse<AddressLibraryCoordinate>) => {
          if (addressLibraryCoordinate.body) {
            return of(addressLibraryCoordinate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AddressLibraryCoordinate());
  }
}

export const addressLibraryCoordinateRoute: Routes = [
  {
    path: '',
    component: AddressLibraryCoordinateComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'baiduApp.addressLibraryCoordinate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddressLibraryCoordinateDetailComponent,
    resolve: {
      addressLibraryCoordinate: AddressLibraryCoordinateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baiduApp.addressLibraryCoordinate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddressLibraryCoordinateUpdateComponent,
    resolve: {
      addressLibraryCoordinate: AddressLibraryCoordinateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baiduApp.addressLibraryCoordinate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddressLibraryCoordinateUpdateComponent,
    resolve: {
      addressLibraryCoordinate: AddressLibraryCoordinateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baiduApp.addressLibraryCoordinate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
