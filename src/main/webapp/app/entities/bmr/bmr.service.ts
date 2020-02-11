import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class BmrService {
  constructor(protected http: HttpClient) {}
}
