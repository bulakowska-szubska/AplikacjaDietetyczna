import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPrzepis, Przepis } from 'app/shared/model/przepis.model';
import { PrzepisService } from './przepis.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-przepis-update',
  templateUrl: './przepis-update.component.html'
})
export class PrzepisUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    nazwa: [],
    typPrzepisu: [],
    zdjecie: [],
    zdjecieContentType: [],
    opis: [],
    kalorieSuma: [],
    user: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected przepisService: PrzepisService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ przepis }) => {
      this.updateForm(przepis);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(przepis: IPrzepis) {
    this.editForm.patchValue({
      id: przepis.id,
      nazwa: przepis.nazwa,
      typPrzepisu: przepis.typPrzepisu,
      zdjecie: przepis.zdjecie,
      zdjecieContentType: przepis.zdjecieContentType,
      opis: przepis.opis,
      kalorieSuma: przepis.kalorieSuma,
      user: przepis.user
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
    const przepis = this.createFromForm();
    if (przepis.id !== undefined) {
      this.subscribeToSaveResponse(this.przepisService.update(przepis));
    } else {
      this.subscribeToSaveResponse(this.przepisService.create(przepis));
    }
  }

  private createFromForm(): IPrzepis {
    return {
      ...new Przepis(),
      id: this.editForm.get(['id']).value,
      nazwa: this.editForm.get(['nazwa']).value,
      typPrzepisu: this.editForm.get(['typPrzepisu']).value,
      zdjecieContentType: this.editForm.get(['zdjecieContentType']).value,
      zdjecie: this.editForm.get(['zdjecie']).value,
      opis: this.editForm.get(['opis']).value,
      kalorieSuma: this.editForm.get(['kalorieSuma']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrzepis>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
