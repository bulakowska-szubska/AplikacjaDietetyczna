import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class KontaktService {
  public resourceUrlGmailKontaktFromUser = SERVER_API_URL + 'api/wyslijMailKontakt';

  constructor(protected http: HttpClient) {}

  sendGmailKontaktFromUser(formData: FormData) {
    return this.http.post(this.resourceUrlGmailKontaktFromUser, formData).subscribe();
  }
}
