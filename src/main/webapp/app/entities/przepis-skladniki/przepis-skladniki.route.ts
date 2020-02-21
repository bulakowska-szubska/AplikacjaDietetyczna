import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';
import { PrzepisSkladnikiService } from './przepis-skladniki.service';
import { PrzepisSkladnikiComponent } from './przepis-skladniki.component';
import { PrzepisSkladnikiDetailComponent } from './przepis-skladniki-detail.component';
import { PrzepisSkladnikiUpdateComponent } from './przepis-skladniki-update.component';
import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

@Injectable({ providedIn: 'root' })
export class PrzepisSkladnikiResolve implements Resolve<IPrzepisSkladniki> {
  constructor(private service: PrzepisSkladnikiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrzepisSkladniki> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((przepisSkladniki: HttpResponse<PrzepisSkladniki>) => przepisSkladniki.body));
    }
    return of(new PrzepisSkladniki());
  }
}

export const przepisSkladnikiRoute: Routes = [
  {
    path: '',
    component: PrzepisSkladnikiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Składniki Przepisu'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrzepisSkladnikiDetailComponent,
    resolve: {
      przepisSkladniki: PrzepisSkladnikiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Składniki Przepisu'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrzepisSkladnikiUpdateComponent,
    resolve: {
      przepisSkladniki: PrzepisSkladnikiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Składniki Przepisu'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrzepisSkladnikiUpdateComponent,
    resolve: {
      przepisSkladniki: PrzepisSkladnikiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Składniki Przepisu'
    },
    canActivate: [UserRouteAccessService]
  }
];
