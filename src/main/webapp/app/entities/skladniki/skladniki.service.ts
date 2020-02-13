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
  private resourceUrlAlkohole = SERVER_API_URL + 'api/skladniki/alkohole';
  private resourceUrlOwoce = SERVER_API_URL + 'api/skladniki/owoce';
  private resourceUrlWarzywa = SERVER_API_URL + 'api/skladniki/warzywa';
  private resourceUrlSery = SERVER_API_URL + 'api/skladniki/sery';
  private resourceUrlPieczywo = SERVER_API_URL + 'api/skladniki/pieczywo';
  private resourceUrlPrzyprawy = SERVER_API_URL + 'api/skladniki/przyprawy';
  private resourceUrlZboza = SERVER_API_URL + 'api/skladniki/zboza';
  private resourceUrlMieso = SERVER_API_URL + 'api/skladniki/mieso';
  private resourceUrlSosy = SERVER_API_URL + 'api/skladniki/sosy';
  private resourceUrlSoki = SERVER_API_URL + 'api/skladniki/soki';
  private resourceUrlPieczenie = SERVER_API_URL + 'api/skladniki/pieczenie';
  private resourceUrlPizza = SERVER_API_URL + 'api/skladniki/pizza';
  private resourceUrlRyby = SERVER_API_URL + 'api/skladniki/ryby';
  private resourceUrlNapoje = SERVER_API_URL + 'api/skladniki/napoje';

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

  queryAllAlkohole(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlAlkohole, { params: options, observe: 'response' });
  }

  queryAllNapoje(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlNapoje, { params: options, observe: 'response' });
  }

  queryAllPizza(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlPizza, { params: options, observe: 'response' });
  }

  queryAllRyby(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlRyby, { params: options, observe: 'response' });
  }

  queryAllPieczenie(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlPieczenie, { params: options, observe: 'response' });
  }

  queryAllSoki(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlSoki, { params: options, observe: 'response' });
  }

  queryAllSosy(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlSosy, { params: options, observe: 'response' });
  }

  queryAllMieso(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlMieso, { params: options, observe: 'response' });
  }

  queryAllZboza(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlZboza, { params: options, observe: 'response' });
  }

  queryAllPrzyprawy(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlPrzyprawy, { params: options, observe: 'response' });
  }

  queryAllPieczywo(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlPieczywo, { params: options, observe: 'response' });
  }

  queryAllSery(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlSery, { params: options, observe: 'response' });
  }

  queryAllWarzywa(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlWarzywa, { params: options, observe: 'response' });
  }

  queryAllOwoce(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkladniki[]>(this.resourceUrlOwoce, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
