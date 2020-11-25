import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { BaiduTestModule } from '../../../test.module';
import { AddressLibraryCoordinateComponent } from 'app/entities/address-library-coordinate/address-library-coordinate.component';
import { AddressLibraryCoordinateService } from 'app/entities/address-library-coordinate/address-library-coordinate.service';
import { AddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';

describe('Component Tests', () => {
  describe('AddressLibraryCoordinate Management Component', () => {
    let comp: AddressLibraryCoordinateComponent;
    let fixture: ComponentFixture<AddressLibraryCoordinateComponent>;
    let service: AddressLibraryCoordinateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaiduTestModule],
        declarations: [AddressLibraryCoordinateComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(AddressLibraryCoordinateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressLibraryCoordinateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressLibraryCoordinateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AddressLibraryCoordinate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.addressLibraryCoordinates && comp.addressLibraryCoordinates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AddressLibraryCoordinate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.addressLibraryCoordinates && comp.addressLibraryCoordinates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
