import { Routes } from '@angular/router';
import { SklepyComponent } from 'app/entities/sklepy/sklepy.component';

export const SklepyRoute: Routes = [
  {
    path: '',
    component: SklepyComponent,
    data: {
      pageTitle: 'Sklepy Eko'
    }
  }
];
