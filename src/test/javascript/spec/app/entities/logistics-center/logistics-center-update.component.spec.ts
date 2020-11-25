import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BaiduTestModule } from '../../../test.module';
import { LogisticsCenterUpdateComponent } from 'app/entities/logistics-center/logistics-center-update.component';
import { LogisticsCenterService } from 'app/entities/logistics-center/logistics-center.service';
import { LogisticsCenter } from 'app/shared/model/logistics-center.model';

describe('Component Tests', () => {
  describe('LogisticsCenter Management Update Component', () => {
    let comp: LogisticsCenterUpdateComponent;
    let fixture: ComponentFixture<LogisticsCenterUpdateComponent>;
    let service: LogisticsCenterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaiduTestModule],
        declarations: [LogisticsCenterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LogisticsCenterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogisticsCenterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogisticsCenterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LogisticsCenter(123);
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
        const entity = new LogisticsCenter();
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
