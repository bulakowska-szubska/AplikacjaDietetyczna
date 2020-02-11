import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-kontakt',
  templateUrl: './kontakt.component.html'
})
export class KontaktComponent implements OnInit {
  imieNazwisko: string;
  temat: string;
  email: string;
  textArea: string;
  submitted = false;

  form = new FormGroup({
    imieNazwisko: new FormControl('', [Validators.required]),
    temat: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    textArea: new FormControl('')
  });

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected eventManager: JhiEventManager) {}

  ngOnInit() {}

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;

    this.imieNazwisko = this.form.get('imieNazwisko').value;
    this.temat = this.form.get('temat').value;
    this.email = this.form.get('email').value;
    this.textArea = this.form.get('textArea').value;

    // email wyslij

    this.resetFormularz();
  }

  resetFormularz() {
    this.form.reset({ imieNazwisko: '', temat: '', email: '', textArea: '' });
  }
}
