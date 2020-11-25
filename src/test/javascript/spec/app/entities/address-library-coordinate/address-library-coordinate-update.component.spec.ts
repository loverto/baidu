import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BaiduTestModule } from '../../../test.module';
import { AddressLibraryCoordinateUpdateComponent } from 'app/entities/address-library-coordinate/address-library-coordinate-update.component';
import { AddressLibraryCoordinateService } from 'app/entities/address-library-coordinate/address-library-coordinate.service';
import { AddressLibraryCoordinate } from 'app/shared/model/address-library-coordinate.model';

describe('Component Tests', () => {
  describe('AddressLibraryCoordinate Management Update Component', () => {
    let comp: AddressLibraryCoordinateUpdateComponent;
    let fixture: ComponentFixture<AddressLibraryCoordinateUpdateComponent>;
    let service: AddressLibraryCoordinateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaiduTestModule],
        declarations: [AddressLibraryCoordinateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AddressLibraryCoordinateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressLibraryCoordinateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressLibraryCoordinateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AddressLibraryCoordinate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AddressLibraryCoordinate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
