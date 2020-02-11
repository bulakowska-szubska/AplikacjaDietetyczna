import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-bmi',
  templateUrl: './bmi.component.html'
})
export class BmiComponent implements OnInit {
  numericPattern = '^-?[0-9]\\d*(\\.\\d{1,2})?$';
  waga: number;
  wzrost: number;
  bmi: number;
  submitted = false;
  komunikatBmi: string;
  kategoriaBmi: string;
  wagaBmi: string;

  form = new FormGroup({
    waga: new FormControl('', [Validators.required, Validators.pattern(this.numericPattern)]),
    wzrost: new FormControl('', [Validators.required, Validators.pattern(this.numericPattern)])
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
      this.bmi = this.waga / (((this.wzrost / 100) * this.wzrost) / 100);
      this.submitted = true;
      this.zakresBmi();
    }
  }

  resetValue() {
    this.form.reset({ waga: '', wzrost: '' });
    this.submitted = false;
  }

  zakresBmi() {
    if (this.bmi < 16) {
      this.kategoriaBmi = 'Wygłodzenie';
      this.komunikatBmi = 'Minimalne, ale zwiększony poziom wystąpienia innych problemów zdrowotnych';
      this.wagaBmi = 'Niedowaga';
    } else if (this.bmi >= 16 && this.bmi <= 16.99) {
      this.kategoriaBmi = 'Wychudzenie';
      this.komunikatBmi = 'Minimalne, ale zwiększony poziom wystąpienia innych problemów zdrowotnych';
      this.wagaBmi = 'Niedowaga';
    } else if (this.bmi >= 17 && this.bmi <= 18.49) {
      this.kategoriaBmi = 'niedowaga';
      this.komunikatBmi = 'Minimalne, ale zwiększony poziom wystąpienia innych problemów zdrowotnych';
      this.wagaBmi = 'Niedowaga';
    } else if (this.bmi >= 18.5 && this.bmi <= 24.99) {
      this.kategoriaBmi = 'Pożądana masa ciała';
      this.komunikatBmi = 'Minimalne';
      this.wagaBmi = 'Optimum';
    } else if (this.bmi >= 25 && this.bmi <= 29.99) {
      this.kategoriaBmi = 'Nadwaga';
      this.komunikatBmi = 'Średnie';
      this.wagaBmi = 'Nadwaga';
    } else if (this.bmi >= 30 && this.bmi <= 34.99) {
      this.kategoriaBmi = 'Otyłość I stopnia';
      this.komunikatBmi = 'Wysokie';
      this.wagaBmi = 'Otyłość';
    } else if (this.bmi >= 35 && this.bmi <= 39.99) {
      this.kategoriaBmi = 'Otyłość II stopnia (duża)';
      this.komunikatBmi = 'Bardzo wysokie';
      this.wagaBmi = 'Otyłość';
    } else if (this.bmi >= 40) {
      this.kategoriaBmi = 'Otyłość III stopnia (chorobliwa)';
      this.komunikatBmi = 'Ekstremalny poziom ryzyka';
      this.wagaBmi = 'Otyłość';
    }
  }
}
