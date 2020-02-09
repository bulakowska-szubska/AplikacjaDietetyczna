import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

@Component({
  selector: 'jhi-przepis-skladniki-detail',
  templateUrl: './przepis-skladniki-detail.component.html'
})
export class PrzepisSkladnikiDetailComponent implements OnInit {
  przepisSkladniki: IPrzepisSkladniki;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ przepisSkladniki }) => {
      this.przepisSkladniki = przepisSkladniki;
    });
  }

  previousState() {
    window.history.back();
  }
}
