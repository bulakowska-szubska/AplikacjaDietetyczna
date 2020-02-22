import { Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PrzepisSkladnikiService } from './przepis-skladniki.service';
import { PrzepisSkladnikiDeleteDialogComponent } from './przepis-skladniki-delete-dialog.component';
import { IPrzepis, Przepis } from 'app/shared/model/przepis.model';
import { PrzepisService } from 'app/entities/przepis/przepis.service';
import { FormBuilder } from '@angular/forms';

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

  editForm = this.fb.group({
    nazwa: [],
    typPrzepisu: [],
    zdjecie: [],
    zdjecieContentType: [],
    opis: []
  });

  constructor(
    protected przepisSkladnikiService: PrzepisSkladnikiService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected przepisService: PrzepisService,
    private fb: FormBuilder,
    protected elementRef: ElementRef,
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  private createFromForm(): IPrzepis {
    return {
      ...new Przepis(),
      nazwa: this.editForm.get(['nazwa']).value,
      typPrzepisu: this.editForm.get(['typPrzepisu']).value,
      zdjecieContentType: this.editForm.get(['zdjecieContentType']).value,
      zdjecie: this.editForm.get(['zdjecie']).value,
      opis: this.editForm.get(['opis']).value
    };
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

  dodajPrzepis() {
    const przepis = this.createFromForm();
    this.przepisService.createUserPrzepis(przepis).subscribe();
  }
}
