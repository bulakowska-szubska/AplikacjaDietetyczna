import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { BmiComponent } from 'app/entities/bmi/bmi.component';
import { BmiRoute } from 'app/entities/bmi/bmi.route';

@NgModule({
  imports: [AplikacjaDietetycznaSharedModule, RouterModule.forChild(BmiRoute)],
  declarations: [BmiComponent]
})
export class AplikacjaDietetycznaBmiModule {}
