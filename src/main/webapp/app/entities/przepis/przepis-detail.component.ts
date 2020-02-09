import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPrzepis } from 'app/shared/model/przepis.model';

@Component({
  selector: 'jhi-przepis-detail',
  templateUrl: './przepis-detail.component.html'
})
export class PrzepisDetailComponent implements OnInit {
  przepis: IPrzepis;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ przepis }) => {
      this.przepis = przepis;
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
}
