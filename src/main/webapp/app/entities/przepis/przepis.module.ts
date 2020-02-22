import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { PrzepisComponent } from './przepis.component';
import { PrzepisDetailComponent } from './przepis-detail.component';
import { PrzepisUpdateComponent } from './przepis-update.component';
import { PrzepisDeleteDialogComponent } from './przepis-delete-dialog.component';
import { przepisRoute } from './przepis.route';

@NgModule({
  imports: [AplikacjaDietetycznaSharedModule, RouterModule.forChild(przepisRoute)],
  declarations: [PrzepisComponent, PrzepisDetailComponent, PrzepisUpdateComponent, PrzepisDeleteDialogComponent],
  providers: [PrzepisDetailComponent],
  entryComponents: [PrzepisDeleteDialogComponent]
})
export class AplikacjaDietetycznaPrzepisModule {}
