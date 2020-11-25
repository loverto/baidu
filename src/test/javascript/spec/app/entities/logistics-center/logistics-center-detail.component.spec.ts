import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BaiduTestModule } from '../../../test.module';
import { LogisticsCenterDetailComponent } from 'app/entities/logistics-center/logistics-center-detail.component';
import { LogisticsCenter } from 'app/shared/model/logistics-center.model';

describe('Component Tests', () => {
  describe('LogisticsCenter Management Detail Component', () => {
    let comp: LogisticsCenterDetailComponent;
    let fixture: ComponentFixture<LogisticsCenterDetailComponent>;
    const route = ({ data: of({ logisticsCenter: new LogisticsCenter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BaiduTestModule],
        declarations: [LogisticsCenterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LogisticsCenterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogisticsCenterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logisticsCenter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logisticsCenter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
