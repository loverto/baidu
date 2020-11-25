import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BaiduTestModule } from '../../../test.module';
import { AddressLibraryCoordinateDetailComponent } from 'app/entities/address-library-coordinate/address-library-coordinate-detail.component';
import { AddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';

describe('Component Tests', () => {
  describe('AddressLibraryCoordinate Management Detail Component', () => {
    let comp: AddressLibraryCoordinateDetailComponent;
    let fixture: ComponentFixture<AddressLibraryCoordinateDetailComponent>;
    const route = ({ data: of({ addressLibraryCoordinate: new AddressLibraryCoordinate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaiduTestModule],
        declarations: [AddressLibraryCoordinateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AddressLibraryCoordinateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AddressLibraryCoordinateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load addressLibraryCoordinate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.addressLibraryCoordinate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
