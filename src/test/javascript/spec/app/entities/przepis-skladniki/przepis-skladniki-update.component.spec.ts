import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { PrzepisSkladnikiUpdateComponent } from 'app/entities/przepis-skladniki/przepis-skladniki-update.component';
import { PrzepisSkladnikiService } from 'app/entities/przepis-skladniki/przepis-skladniki.service';
import { PrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

describe('Component Tests', () => {
  describe('PrzepisSkladniki Management Update Component', () => {
    let comp: PrzepisSkladnikiUpdateComponent;
    let fixture: ComponentFixture<PrzepisSkladnikiUpdateComponent>;
    let service: PrzepisSkladnikiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [PrzepisSkladnikiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrzepisSkladnikiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrzepisSkladnikiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrzepisSkladnikiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrzepisSkladniki(123);
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
        const entity = new PrzepisSkladniki();
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
