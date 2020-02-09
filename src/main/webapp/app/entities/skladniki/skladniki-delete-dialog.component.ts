import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkladniki } from 'app/shared/model/skladniki.model';
import { SkladnikiService } from './skladniki.service';

@Component({
  templateUrl: './skladniki-delete-dialog.component.html'
})
export class SkladnikiDeleteDialogComponent {
  skladniki: ISkladniki;

  constructor(protected skladnikiService: SkladnikiService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skladnikiService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'skladnikiListModification',
        content: 'Deleted an skladniki'
      });
      this.activeModal.dismiss(true);
    });
  }
}
