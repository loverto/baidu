import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';
import { AddressLibraryCoordinateService } from './address-library-coordinate.service';

@Component({
  templateUrl: './address-library-coordinate-delete-dialog.component.html',
})
export class AddressLibraryCoordinateDeleteDialogComponent {
  addressLibraryCoordinate?: IAddressLibraryCoordinate;

  constructor(
    protected addressLibraryCoordinateService: AddressLibraryCoordinateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.addressLibraryCoordinateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('addressLibraryCoordinateListModification');
      this.activeModal.close();
    });
  }
}
