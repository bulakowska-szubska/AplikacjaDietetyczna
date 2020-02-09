import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { PrzepisUpdateComponent } from 'app/entities/przepis/przepis-update.component';
import { PrzepisService } from 'app/entities/przepis/przepis.service';
import { Przepis } from 'app/shared/model/przepis.model';

describe('Component Tests', () => {
  describe('Przepis Management Update Component', () => {
    let comp: PrzepisUpdateComponent;
    let fixture: ComponentFixture<PrzepisUpdateComponent>;
    let service: PrzepisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [PrzepisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrzepisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrzepisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrzepisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Przepis(123);
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
        const entity = new Przepis();
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
