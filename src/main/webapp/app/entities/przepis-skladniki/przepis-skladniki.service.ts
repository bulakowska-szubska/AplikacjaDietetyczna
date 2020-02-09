import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';

type EntityResponseType = HttpResponse<IPrzepisSkladniki>;
type EntityArrayResponseType = HttpResponse<IPrzepisSkladniki[]>;

@Injectable({ providedIn: 'root' })
export class PrzepisSkladnikiService {
  public resourceUrl = SERVER_API_URL + 'api/przepis-skladnikis';

  constructor(protected http: HttpClient) {}

  create(przepisSkladniki: IPrzepisSkladniki): Observable<EntityResponseType> {
    return this.http.post<IPrzepisSkladniki>(this.resourceUrl, przepisSkladniki, { observe: 'response' });
  }

  update(przepisSkladniki: IPrzepisSkladniki): Observable<EntityResponseType> {
    return this.http.put<IPrzepisSkladniki>(this.resourceUrl, przepisSkladniki, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrzepisSkladniki>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrzepisSkladniki[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
