import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrzepis } from 'app/shared/model/przepis.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PrzepisService } from './przepis.service';
import { PrzepisDeleteDialogComponent } from './przepis-delete-dialog.component';
import { PrzepisDetailComponent } from 'app/entities/przepis/przepis-detail.component';
import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

@Component({
  selector: 'jhi-przepis',
  templateUrl: './przepis.component.html'
})
export class PrzepisComponent implements OnInit, OnDestroy {
  przepis: IPrzepis[];
  przepisSkladniki: IPrzepisSkladniki[];
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
    protected przepisService: PrzepisService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected przepisDetailComponent: PrzepisDetailComponent
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
    this.przepisService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPrzepis[]>) => this.paginatePrzepis(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/przepis'], {
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
      '/przepis',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPrzepis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrzepis) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPrzepis() {
    this.eventSubscriber = this.eventManager.subscribe('przepisListModification', () => this.loadAll());
  }

  delete(przepis: IPrzepis) {
    const modalRef = this.modalService.open(PrzepisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.przepis = przepis;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePrzepis(data: IPrzepis[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.przepis = data;
  }

  getAllPrzepisSkladniki(przepisId: number) {
    this.przepisDetailComponent.getAllSkladnikiPrzepis(przepisId);
  }
}
