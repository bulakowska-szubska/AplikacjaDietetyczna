import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { PrzepisDetailComponent } from 'app/entities/przepis/przepis-detail.component';
import { Przepis } from 'app/shared/model/przepis.model';

describe('Component Tests', () => {
  describe('Przepis Management Detail Component', () => {
    let comp: PrzepisDetailComponent;
    let fixture: ComponentFixture<PrzepisDetailComponent>;
    const route = ({ data: of({ przepis: new Przepis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [PrzepisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrzepisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrzepisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.przepis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
