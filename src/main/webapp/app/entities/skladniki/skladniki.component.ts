import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISkladniki } from 'app/shared/model/skladniki.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SkladnikiService } from './skladniki.service';
import { SkladnikiDeleteDialogComponent } from './skladniki-delete-dialog.component';
import { FormControl, Validators } from '@angular/forms';
import { PrzepisSkladnikiService } from 'app/entities/przepis-skladniki/przepis-skladniki.service';

@Component({
  selector: 'jhi-skladniki',
  templateUrl: './skladniki.component.html'
})
export class SkladnikiComponent implements OnInit, OnDestroy {
  skladnikis: ISkladniki[];
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
  ilosc = new FormControl(1, [Validators.required, Validators.min(1), Validators.pattern('^(0|[1-9][0-9]*)$')]);

  constructor(
    protected skladnikiService: SkladnikiService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected przepisSkladnikiService: PrzepisSkladnikiService
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
    this.skladnikiService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/skladniki'], {
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
      '/skladniki',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSkladnikis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISkladniki) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInSkladnikis() {
    this.eventSubscriber = this.eventManager.subscribe('skladnikiListModification', () => this.loadAll());
  }

  delete(skladniki: ISkladniki) {
    const modalRef = this.modalService.open(SkladnikiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.skladniki = skladniki;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSkladnikis(data: ISkladniki[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.skladnikis = data;
  }

  getAllSkladniki() {
    this.loadAll();
  }

  getAllAlkohole() {
    this.skladnikiService.queryAllAlkohole().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllSoki() {
    this.skladnikiService.queryAllSoki().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllMieso() {
    this.skladnikiService.queryAllMieso().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllNapoje() {
    this.skladnikiService.queryAllNapoje().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllOwoce() {
    this.skladnikiService.queryAllOwoce().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllPieczenie() {
    this.skladnikiService
      .queryAllPieczenie()
      .subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllPieczywo() {
    this.skladnikiService.queryAllPieczywo().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllPizza() {
    this.skladnikiService.queryAllPizza().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllPrzyprawy() {
    this.skladnikiService
      .queryAllPrzyprawy()
      .subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllRyby() {
    this.skladnikiService.queryAllRyby().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllSery() {
    this.skladnikiService.queryAllSery().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllSosy() {
    this.skladnikiService.queryAllSosy().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllWarzywa() {
    this.skladnikiService.queryAllWarzywa().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  getAllZboza() {
    this.skladnikiService.queryAllZboza().subscribe((res: HttpResponse<ISkladniki[]>) => this.paginateSkladnikis(res.body, res.headers));
  }

  dodajSkladnikiDoKoszyka(skladnik: ISkladniki, ilosc: number) {
    this.przepisSkladnikiService.createPrzepisSkladnikRecord(skladnik, ilosc);
  }
}
