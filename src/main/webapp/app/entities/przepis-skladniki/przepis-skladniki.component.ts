import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PrzepisSkladnikiService } from './przepis-skladniki.service';
import { PrzepisSkladnikiDeleteDialogComponent } from './przepis-skladniki-delete-dialog.component';

@Component({
  selector: 'jhi-przepis-skladniki',
  templateUrl: './przepis-skladniki.component.html'
})
export class PrzepisSkladnikiComponent implements OnInit, OnDestroy {
  przepisSkladnikis: IPrzepisSkladniki[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected przepisSkladnikiService: PrzepisSkladnikiService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.przepisSkladnikiService
      .loadUserSkladnikiOnly({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPrzepisSkladniki[]>) => this.paginatePrzepisSkladnikis(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/przepis-skladniki'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/przepis-skladniki',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPrzepisSkladnikis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrzepisSkladniki) {
    return item.id;
  }

  registerChangeInPrzepisSkladnikis() {
    this.eventSubscriber = this.eventManager.subscribe('przepisSkladnikiListModification', () => this.loadAll());
  }

  delete(przepisSkladniki: IPrzepisSkladniki) {
    const modalRef = this.modalService.open(PrzepisSkladnikiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.przepisSkladniki = przepisSkladniki;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePrzepisSkladnikis(data: IPrzepisSkladniki[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.przepisSkladnikis = data;
  }
}
