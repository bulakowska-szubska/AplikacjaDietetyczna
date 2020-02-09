import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISkladniki } from 'app/shared/model/skladniki.model';

@Component({
  selector: 'jhi-skladniki-detail',
  templateUrl: './skladniki-detail.component.html'
})
export class SkladnikiDetailComponent implements OnInit {
  skladniki: ISkladniki;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skladniki }) => {
      this.skladniki = skladniki;
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
