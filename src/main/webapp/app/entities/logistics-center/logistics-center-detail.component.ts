import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogisticsCenter } from 'app/shared/model/logistics-center.model';

@Component({
  selector: 'jhi-logistics-center-detail',
  templateUrl: './logistics-center-detail.component.html',
})
export class LogisticsCenterDetailComponent implements OnInit {
  logisticsCenter: ILogisticsCenter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logisticsCenter }) => (this.logisticsCenter = logisticsCenter));
  }

  previousState(): void {
    window.history.back();
  }
}
