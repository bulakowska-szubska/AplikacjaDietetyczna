import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';
import { PrzepisSkladnikiService } from './przepis-skladniki.service';

@Component({
  templateUrl: './przepis-skladniki-delete-dialog.component.html'
})
export class PrzepisSkladnikiDeleteDialogComponent {
  przepisSkladniki: IPrzepisSkladniki;

  constructor(
    protected przepisSkladnikiService: PrzepisSkladnikiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.przepisSkladnikiService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'przepisSkladnikiListModification',
        content: 'Deleted an przepisSkladniki'
      });
      this.activeModal.dismiss(true);
    });
  }
}
