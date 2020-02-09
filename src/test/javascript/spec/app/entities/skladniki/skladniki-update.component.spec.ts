import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { SkladnikiUpdateComponent } from 'app/entities/skladniki/skladniki-update.component';
import { SkladnikiService } from 'app/entities/skladniki/skladniki.service';
import { Skladniki } from 'app/shared/model/skladniki.model';

describe('Component Tests', () => {
  describe('Skladniki Management Update Component', () => {
    let comp: SkladnikiUpdateComponent;
    let fixture: ComponentFixture<SkladnikiUpdateComponent>;
    let service: SkladnikiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [SkladnikiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkladnikiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkladnikiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkladnikiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Skladniki(123);
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
        const entity = new Skladniki();
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
