import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPrzepisSkladniki, PrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';
import { PrzepisSkladnikiService } from './przepis-skladniki.service';
import { ISkladniki } from 'app/shared/model/skladniki.model';
import { SkladnikiService } from 'app/entities/skladniki/skladniki.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-przepis-skladniki-update',
  templateUrl: './przepis-skladniki-update.component.html'
})
export class PrzepisSkladnikiUpdateComponent implements OnInit {
  isSaving: boolean;

  skladnikis: ISkladniki[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    ilosc: [],
    kalorieIlosc: [],
    przepisId: [],
    skladniki: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected przepisSkladnikiService: PrzepisSkladnikiService,
    protected skladnikiService: SkladnikiService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ przepisSkladniki }) => {
      this.updateForm(przepisSkladniki);
    });
    this.skladnikiService
      .query()
      .subscribe((res: HttpResponse<ISkladniki[]>) => (this.skladnikis = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(przepisSkladniki: IPrzepisSkladniki) {
    this.editForm.patchValue({
      id: przepisSkladniki.id,
      ilosc: przepisSkladniki.ilosc,
      kalorieIlosc: przepisSkladniki.kalorieIlosc,
      przepisId: przepisSkladniki.przepisId,
      skladniki: przepisSkladniki.skladniki,
      user: przepisSkladniki.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const przepisSkladniki = this.createFromForm();
    if (przepisSkladniki.id !== undefined) {
      this.subscribeToSaveResponse(this.przepisSkladnikiService.update(przepisSkladniki));
    } else {
      this.subscribeToSaveResponse(this.przepisSkladnikiService.create(przepisSkladniki));
    }
  }

  private createFromForm(): IPrzepisSkladniki {
    return {
      ...new PrzepisSkladniki(),
      id: this.editForm.get(['id']).value,
      ilosc: this.editForm.get(['ilosc']).value,
      kalorieIlosc: this.editForm.get(['kalorieIlosc']).value,
      przepisId: this.editForm.get(['przepisId']).value,
      skladniki: this.editForm.get(['skladniki']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrzepisSkladniki>>) {
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

  trackSkladnikiById(index: number, item: ISkladniki) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
