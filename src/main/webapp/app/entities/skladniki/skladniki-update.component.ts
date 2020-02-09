import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISkladniki, Skladniki } from 'app/shared/model/skladniki.model';
import { SkladnikiService } from './skladniki.service';

@Component({
  selector: 'jhi-skladniki-update',
  templateUrl: './skladniki-update.component.html'
})
export class SkladnikiUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nazwa: [],
    zdjecie: [],
    zdjecieContentType: [],
    jednostka: [],
    kategoria: [],
    kalorieSto: [],
    kalorieJednostka: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected skladnikiService: SkladnikiService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skladniki }) => {
      this.updateForm(skladniki);
    });
  }

  updateForm(skladniki: ISkladniki) {
    this.editForm.patchValue({
      id: skladniki.id,
      nazwa: skladniki.nazwa,
      zdjecie: skladniki.zdjecie,
      zdjecieContentType: skladniki.zdjecieContentType,
      jednostka: skladniki.jednostka,
      kategoria: skladniki.kategoria,
      kalorieSto: skladniki.kalorieSto,
      kalorieJednostka: skladniki.kalorieJednostka
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skladniki = this.createFromForm();
    if (skladniki.id !== undefined) {
      this.subscribeToSaveResponse(this.skladnikiService.update(skladniki));
    } else {
      this.subscribeToSaveResponse(this.skladnikiService.create(skladniki));
    }
  }

  private createFromForm(): ISkladniki {
    return {
      ...new Skladniki(),
      id: this.editForm.get(['id']).value,
      nazwa: this.editForm.get(['nazwa']).value,
      zdjecieContentType: this.editForm.get(['zdjecieContentType']).value,
      zdjecie: this.editForm.get(['zdjecie']).value,
      jednostka: this.editForm.get(['jednostka']).value,
      kategoria: this.editForm.get(['kategoria']).value,
      kalorieSto: this.editForm.get(['kalorieSto']).value,
      kalorieJednostka: this.editForm.get(['kalorieJednostka']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkladniki>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
