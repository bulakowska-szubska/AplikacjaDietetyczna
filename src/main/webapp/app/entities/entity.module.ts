import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'skladniki',
        loadChildren: () => import('./skladniki/skladniki.module').then(m => m.AplikacjaDietetycznaSkladnikiModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AplikacjaDietetycznaEntityModule {}
