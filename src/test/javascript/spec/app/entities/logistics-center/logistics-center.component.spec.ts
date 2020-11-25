import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { BaiduTestModule } from '../../../test.module';
import { LogisticsCenterComponent } from 'app/entities/logistics-center/logistics-center.component';
import { LogisticsCenterService } from 'app/entities/logistics-center/logistics-center.service';
import { LogisticsCenter } from 'app/shared/model/logistics-center.model';

describe('Component Tests', () => {
  describe('LogisticsCenter Management Component', () => {
    let comp: LogisticsCenterComponent;
    let fixture: ComponentFixture<LogisticsCenterComponent>;
    let service: LogisticsCenterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaiduTestModule],
        declarations: [LogisticsCenterComponent],
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
        .overrideTemplate(LogisticsCenterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogisticsCenterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogisticsCenterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LogisticsCenter(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.logisticsCenters && comp.logisticsCenters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LogisticsCenter(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.logisticsCenters && comp.logisticsCenters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
