import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { KontaktService } from 'app/entities/kontakt/kontakt.service';

@Component({
  selector: 'jhi-kontakt',
  templateUrl: './kontakt.component.html'
})
export class KontaktComponent implements OnInit {
  imieNazwisko: string;
  temat: string;
  email: string;
  wiadomosc: string;
  submitted = false;

  form = new FormGroup({
    imieNazwisko: new FormControl('', [Validators.required]),
    temat: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    wiadomosc: new FormControl('')
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected kontaktService: KontaktService
  ) {
    this.kontaktService = kontaktService;
  }

  ngOnInit() {}

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;

    this.imieNazwisko = this.form.get('imieNazwisko').value;
    this.temat = this.form.get('temat').value;
    this.email = this.form.get('email').value;
    this.wiadomosc = this.form.get('wiadomosc').value;

    this.sendGmailKontakt();

    this.resetFormularz();
  }

  resetFormularz() {
    this.form.reset({ imieNazwisko: '', temat: '', email: '', wiadomosc: '' });
  }

  sendGmailKontakt() {
    const formularz = new FormData();

    formularz.append('imieNazwisko', this.imieNazwisko);
    formularz.append('temat', this.temat);
    formularz.append('email', this.email);
    formularz.append('wiadomosc', this.wiadomosc);

    this.kontaktService.sendGmailKontaktFromUser(formularz);
  }
}
