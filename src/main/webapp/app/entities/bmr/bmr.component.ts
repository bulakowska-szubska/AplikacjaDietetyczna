import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-bmr',
  templateUrl: './bmr.component.html'
})
export class BmrComponent implements OnInit {
  numericPattern = '^-?[0-9]\\d*(\\.\\d{1,2})?$';
  waga: number;
  wzrost: number;
  wiek: number;
  bmrHarrisBenedict: number;
  bmrMifflinStJeor: number;
  submitted = false;
  plec: string;

  form = new FormGroup({
    waga: new FormControl('', [Validators.required, Validators.pattern(this.numericPattern)]),
    wzrost: new FormControl('', [Validators.required, Validators.pattern(this.numericPattern)]),
    wiek: new FormControl('', [Validators.required, Validators.pattern(this.numericPattern)]),
    plec: new FormControl('')
  });

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected eventManager: JhiEventManager) {}

  ngOnInit() {}

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    if (this.form.status === 'VALID') {
      this.waga = this.form.get('waga').value;
      this.wzrost = this.form.get('wzrost').value;
      this.wiek = this.form.get('wiek').value;
      this.plec = this.form.get('plec').value;

      if (this.plec === 'mezczyzna') {
        this.bmrHarrisBenedict = 13.397 * this.waga + 4.799 * this.wzrost - 5.677 * this.wiek + 88.362;
        this.bmrMifflinStJeor = 10 * this.waga + 6.25 * this.wzrost - 5 * this.wiek + 5;
      } else if (this.plec === 'kobieta') {
        this.bmrHarrisBenedict = 9.247 * this.waga + 3.098 * this.wzrost - 4.33 * this.wiek + 447.593;
        this.bmrMifflinStJeor = 10 * this.waga + 6.25 * this.wzrost - 5 * this.wiek - 161;
      }
      this.submitted = true;
    }
  }

  resetValue() {
    this.form.reset({ waga: '', wzrost: '', wiek: '' });
    this.submitted = false;
  }
}
