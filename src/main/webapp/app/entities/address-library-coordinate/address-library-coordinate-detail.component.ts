import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';

@Component({
  selector: 'jhi-address-library-coordinate-detail',
  templateUrl: './address-library-coordinate-detail.component.html',
})
export class AddressLibraryCoordinateDetailComponent implements OnInit {
  addressLibraryCoordinate: IAddressLibraryCoordinate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addressLibraryCoordinate }) => (this.addressLibraryCoordinate = addressLibraryCoordinate));
  }

  previousState(): void {
    window.history.back();
  }
}
