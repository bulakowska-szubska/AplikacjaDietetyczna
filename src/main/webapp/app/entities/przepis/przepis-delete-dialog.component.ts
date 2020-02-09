import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrzepis } from 'app/shared/model/przepis.model';
import { PrzepisService } from './przepis.service';

@Component({
  templateUrl: './przepis-delete-dialog.component.html'
})
export class PrzepisDeleteDialogComponent {
  przepis: IPrzepis;

  constructor(protected przepisService: PrzepisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.przepisService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'przepisListModification',
        content: 'Deleted an przepis'
      });
      this.activeModal.dismiss(true);
    });
  }
}
