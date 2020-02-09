import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { SkladnikiComponent } from './skladniki.component';
import { SkladnikiDetailComponent } from './skladniki-detail.component';
import { SkladnikiUpdateComponent } from './skladniki-update.component';
import { SkladnikiDeleteDialogComponent } from './skladniki-delete-dialog.component';
import { skladnikiRoute } from './skladniki.route';

@NgModule({
  imports: [AplikacjaDietetycznaSharedModule, RouterModule.forChild(skladnikiRoute)],
  declarations: [SkladnikiComponent, SkladnikiDetailComponent, SkladnikiUpdateComponent, SkladnikiDeleteDialogComponent],
  entryComponents: [SkladnikiDeleteDialogComponent]
})
export class AplikacjaDietetycznaSkladnikiModule {}
