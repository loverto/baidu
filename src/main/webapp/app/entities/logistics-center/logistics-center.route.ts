import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILogisticsCenter, LogisticsCenter } from 'app/shared/model/logistics-center.model';
import { LogisticsCenterService } from './logistics-center.service';
import { LogisticsCenterComponent } from './logistics-center.component';
import { LogisticsCenterDetailComponent } from './logistics-center-detail.component';
import { LogisticsCenterUpdateComponent } from './logistics-center-update.component';

@Injectable({ providedIn: 'root' })
export class LogisticsCenterResolve implements Resolve<ILogisticsCenter> {
  constructor(private service: LogisticsCenterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogisticsCenter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((logisticsCenter: HttpResponse<LogisticsCenter>) => {
          if (logisticsCenter.body) {
            return of(logisticsCenter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogisticsCenter());
  }
}

export const logisticsCenterRoute: Routes = [
  {
    path: '',
    component: LogisticsCenterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'baiduApp.logisticsCenter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LogisticsCenterDetailComponent,
    resolve: {
      logisticsCenter: LogisticsCenterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baiduApp.logisticsCenter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogisticsCenterUpdateComponent,
    resolve: {
      logisticsCenter: LogisticsCenterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baiduApp.logisticsCenter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LogisticsCenterUpdateComponent,
    resolve: {
      logisticsCenter: LogisticsCenterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'baiduApp.logisticsCenter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
