import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISkladniki } from 'app/shared/model/skladniki.model';

type EntityResponseType = HttpResponse<ISkladniki>;
type EntityArrayResponseType = HttpResponse<ISkladniki[]>;

@Injectable({ providedIn: 'root' })
export class SkladnikiService {
  public resourceUrl = SERVER_API_URL + 'api/skladnikis';

  constructor(protected http: HttpClient) {}

  create(skladniki: ISkladniki): Observable<EntityResponseType> {
    return this.http.post<ISkladniki>(this.resourceUrl, skladniki, { observe: 'response' });
  }

  update(skladniki: ISkladniki): Observable<EntityResponseType> {
    return this.http.put<ISkladniki>(this.resourceUrl, skladniki, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkladniki>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
