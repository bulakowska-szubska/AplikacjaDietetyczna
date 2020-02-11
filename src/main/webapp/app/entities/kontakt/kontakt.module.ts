import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { KontaktRoute } from 'app/entities/kontakt/kontakt.route';
import { KontaktComponent } from 'app/entities/kontakt/kontakt.component';

@NgModule({
  imports: [AplikacjaDietetycznaSharedModule, RouterModule.forChild(KontaktRoute), ReactiveFormsModule],
  declarations: [KontaktComponent]
})
export class AplikacjaDietetycznaKontaktModule {}
