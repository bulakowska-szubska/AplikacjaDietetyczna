import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Skladniki } from 'app/shared/model/skladniki.model';
import { SkladnikiService } from './skladniki.service';
import { SkladnikiComponent } from './skladniki.component';
import { SkladnikiDetailComponent } from './skladniki-detail.component';
import { SkladnikiUpdateComponent } from './skladniki-update.component';
import { ISkladniki } from 'app/shared/model/skladniki.model';

@Injectable({ providedIn: 'root' })
export class SkladnikiResolve implements Resolve<ISkladniki> {
  constructor(private service: SkladnikiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISkladniki> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((skladniki: HttpResponse<Skladniki>) => skladniki.body));
    }
    return of(new Skladniki());
  }
}

export const skladnikiRoute: Routes = [
  {
    path: '',
    component: SkladnikiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Składniki'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkladnikiDetailComponent,
    resolve: {
      skladniki: SkladnikiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Składniki'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkladnikiUpdateComponent,
    resolve: {
      skladniki: SkladnikiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Składniki'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkladnikiUpdateComponent,
    resolve: {
      skladniki: SkladnikiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Składniki'
    },
    canActivate: [UserRouteAccessService]
  }
];
