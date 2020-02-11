import { Routes } from '@angular/router';
import { KontaktComponent } from 'app/entities/kontakt/kontakt.component';

export const KontaktRoute: Routes = [
  {
    path: '',
    component: KontaktComponent,
    data: {
      pageTitle: 'Kontakt'
    }
  }
];
