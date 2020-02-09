import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { PrzepisSkladnikiDetailComponent } from 'app/entities/przepis-skladniki/przepis-skladniki-detail.component';
import { PrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

describe('Component Tests', () => {
  describe('PrzepisSkladniki Management Detail Component', () => {
    let comp: PrzepisSkladnikiDetailComponent;
    let fixture: ComponentFixture<PrzepisSkladnikiDetailComponent>;
    const route = ({ data: of({ przepisSkladniki: new PrzepisSkladniki(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [PrzepisSkladnikiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrzepisSkladnikiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrzepisSkladnikiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.przepisSkladniki).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
