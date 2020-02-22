import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPrzepis } from 'app/shared/model/przepis.model';
import { PrzepisSkladnikiService } from 'app/entities/przepis-skladniki/przepis-skladniki.service';
import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

@Component({
  selector: 'jhi-przepis-detail',
  templateUrl: './przepis-detail.component.html'
})
export class PrzepisDetailComponent implements OnInit {
  przepis: IPrzepis;
  przepisSkladniki: IPrzepisSkladniki[];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected przepisSkladnikiService: PrzepisSkladnikiService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ przepis }) => {
      this.przepis = przepis;
      this.getAllSkladnikiPrzepis(przepis.id);
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }

  getAllSkladnikiPrzepis(przepisId: number) {
    this.przepisSkladnikiService.getPrzepisSkladnikiByPrzepisId(przepisId).subscribe();
  }
}
