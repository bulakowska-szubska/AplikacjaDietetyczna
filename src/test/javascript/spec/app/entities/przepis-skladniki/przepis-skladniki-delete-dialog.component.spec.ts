import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AplikacjaDietetycznaTestModule } from '../../../test.module';
import { PrzepisSkladnikiDeleteDialogComponent } from 'app/entities/przepis-skladniki/przepis-skladniki-delete-dialog.component';
import { PrzepisSkladnikiService } from 'app/entities/przepis-skladniki/przepis-skladniki.service';

describe('Component Tests', () => {
  describe('PrzepisSkladniki Management Delete Component', () => {
    let comp: PrzepisSkladnikiDeleteDialogComponent;
    let fixture: ComponentFixture<PrzepisSkladnikiDeleteDialogComponent>;
    let service: PrzepisSkladnikiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AplikacjaDietetycznaTestModule],
        declarations: [PrzepisSkladnikiDeleteDialogComponent]
      })
        .overrideTemplate(PrzepisSkladnikiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrzepisSkladnikiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrzepisSkladnikiService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
