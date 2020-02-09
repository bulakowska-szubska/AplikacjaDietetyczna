import { Routes } from '@angular/router';
import { BmiComponent } from 'app/entities/bmi/bmi.component';

export const BmiRoute: Routes = [
  {
    path: '',
    component: BmiComponent,
    data: {
      pageTitle: 'BMI'
    }
  }
];
