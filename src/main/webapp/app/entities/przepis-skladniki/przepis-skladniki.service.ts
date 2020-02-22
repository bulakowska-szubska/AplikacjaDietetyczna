import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrzepisSkladniki, PrzepisSkladniki } from 'app/shared/model/przepis-skladniki.model';
import { ISkladniki } from 'app/shared/model/skladniki.model';

type EntityResponseType = HttpResponse<IPrzepisSkladniki>;
type EntityArrayResponseType = HttpResponse<IPrzepisSkladniki[]>;

@Injectable({ providedIn: 'root' })
export class PrzepisSkladnikiService {
  public resourceUrl = SERVER_API_URL + 'api/przepis-skladnikis';
  public resourceUrlSkladnikPrzepisForUser = SERVER_API_URL + 'api/przepis-skladniki-user';
  public resourceUrlSkladnikPrzepisForUserOnly = SERVER_API_URL + 'api/przepis-skladniki/only-user';
  public resourceUrlAllSkladnikPrzepisByPrzepisId = SERVER_API_URL + 'api/przepis-skladniki-by-przepis-id';

  constructor(protected http: HttpClient) {}

  create(przepisSkladniki: IPrzepisSkladniki): Observable<EntityResponseType> {
    return this.http.post<IPrzepisSkladniki>(this.resourceUrl, przepisSkladniki, { observe: 'response' });
  }

  createPrzepisSkladnikRecordForUser(przepisSkladniki: IPrzepisSkladniki): Observable<EntityResponseType> {
    return this.http.post<IPrzepisSkladniki>(this.resourceUrlSkladnikPrzepisForUser, przepisSkladniki, { observe: 'response' });
  }

  createPrzepisSkladnikRecord(skladnik: ISkladniki, ilosc: number) {
    let przepisSkladniki = new PrzepisSkladniki();

    przepisSkladniki.skladniki = skladnik;
    przepisSkladniki.ilosc = ilosc;
    przepisSkladniki.kalorieIlosc = skladnik.kalorieJednostka * ilosc;
    this.createPrzepisSkladnikRecordForUser(przepisSkladniki).subscribe();
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

  loadUserSkladnikiOnly(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrzepisSkladniki[]>(this.resourceUrlSkladnikPrzepisForUserOnly, { params: options, observe: 'response' });
  }

  getPrzepisSkladnikiByPrzepisId(przepisId: number): Observable<EntityArrayResponseType> {
    return this.http.get<IPrzepisSkladniki[]>(`${this.resourceUrlAllSkladnikPrzepisByPrzepisId}/${przepisId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
