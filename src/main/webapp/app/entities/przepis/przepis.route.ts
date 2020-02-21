import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Przepis } from 'app/shared/model/przepis.model';
import { PrzepisService } from './przepis.service';
import { PrzepisComponent } from './przepis.component';
import { PrzepisDetailComponent } from './przepis-detail.component';
import { PrzepisUpdateComponent } from './przepis-update.component';
import { IPrzepis } from 'app/shared/model/przepis.model';

@Injectable({ providedIn: 'root' })
export class PrzepisResolve implements Resolve<IPrzepis> {
  constructor(private service: PrzepisService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrzepis> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((przepis: HttpResponse<Przepis>) => przepis.body));
    }
    return of(new Przepis());
  }
}

export const przepisRoute: Routes = [
  {
    path: '',
    component: PrzepisComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Przepisy'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrzepisDetailComponent,
    resolve: {
      przepis: PrzepisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Przepisy'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrzepisUpdateComponent,
    resolve: {
      przepis: PrzepisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Przepisy'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrzepisUpdateComponent,
    resolve: {
      przepis: PrzepisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Przepisy'
    },
    canActivate: [UserRouteAccessService]
  }
];
