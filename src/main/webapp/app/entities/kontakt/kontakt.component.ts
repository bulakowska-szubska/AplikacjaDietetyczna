import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-kontakt',
  templateUrl: './kontakt.component.html'
})
export class KontaktComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected eventManager: JhiEventManager) {}

  ngOnInit() {}
}
