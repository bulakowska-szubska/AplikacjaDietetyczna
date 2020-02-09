import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { PrzepisSkladnikiComponent } from './przepis-skladniki.component';
import { PrzepisSkladnikiDetailComponent } from './przepis-skladniki-detail.component';
import { PrzepisSkladnikiUpdateComponent } from './przepis-skladniki-update.component';
import { PrzepisSkladnikiDeleteDialogComponent } from './przepis-skladniki-delete-dialog.component';
import { przepisSkladnikiRoute } from './przepis-skladniki.route';

@NgModule({
  imports: [AplikacjaDietetycznaSharedModule, RouterModule.forChild(przepisSkladnikiRoute)],
  declarations: [
    PrzepisSkladnikiComponent,
    PrzepisSkladnikiDetailComponent,
    PrzepisSkladnikiUpdateComponent,
    PrzepisSkladnikiDeleteDialogComponent
  ],
  entryComponents: [PrzepisSkladnikiDeleteDialogComponent]
})
export class AplikacjaDietetycznaPrzepisSkladnikiModule {}
