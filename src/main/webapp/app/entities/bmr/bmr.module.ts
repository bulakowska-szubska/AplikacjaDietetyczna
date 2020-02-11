import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BmrComponent } from 'app/entities/bmr/bmr.component';
import { BmrRoute } from 'app/entities/bmr/bmr.route';

@NgModule({
  imports: [AplikacjaDietetycznaSharedModule, RouterModule.forChild(BmrRoute), ReactiveFormsModule],
  declarations: [BmrComponent]
})
export class AplikacjaDietetycznaBmrModule {}
