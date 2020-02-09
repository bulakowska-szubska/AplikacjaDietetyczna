import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { SkladnikiDetailComponent } from 'app/entities/skladniki/skladniki-detail.component';
import { Skladniki } from 'app/shared/model/skladniki.model';

describe('Component Tests', () => {
  describe('Skladniki Management Detail Component', () => {
    let comp: SkladnikiDetailComponent;
    let fixture: ComponentFixture<SkladnikiDetailComponent>;
    const route = ({ data: of({ skladniki: new Skladniki(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [SkladnikiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkladnikiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkladnikiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skladniki).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
