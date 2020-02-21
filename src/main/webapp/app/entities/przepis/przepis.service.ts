import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrzepis } from 'app/shared/model/przepis.model';

type EntityResponseType = HttpResponse<IPrzepis>;
type EntityArrayResponseType = HttpResponse<IPrzepis[]>;

@Injectable({ providedIn: 'root' })
export class PrzepisService {
  public resourceUrl = SERVER_API_URL + 'api/przepis';
  private resourceUrlUserPrzepis = SERVER_API_URL + 'api/add-przepis-by-user';

  constructor(protected http: HttpClient) {}

  create(przepis: IPrzepis): Observable<EntityResponseType> {
    return this.http.post<IPrzepis>(this.resourceUrl, przepis, { observe: 'response' });
  }

  createUserPrzepis(przepis: IPrzepis): Observable<EntityResponseType> {
    return this.http.post<IPrzepis>(this.resourceUrlUserPrzepis, przepis, { observe: 'response' });
  }

  update(przepis: IPrzepis): Observable<EntityResponseType> {
    return this.http.put<IPrzepis>(this.resourceUrl, przepis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrzepis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrzepis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
