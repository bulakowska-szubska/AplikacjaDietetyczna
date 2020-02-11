import { Routes } from '@angular/router';
import { BmrComponent } from 'app/entities/bmr/bmr.component';

export const BmrRoute: Routes = [
  {
    path: '',
    component: BmrComponent,
    data: {
      pageTitle: 'BMR'
    }
  }
];
