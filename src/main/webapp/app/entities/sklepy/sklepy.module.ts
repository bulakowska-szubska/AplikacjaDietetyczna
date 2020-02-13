import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { SklepyRoute } from 'app/entities/sklepy/sklepy.route';
import { SklepyComponent } from 'app/entities/sklepy/sklepy.component';
import { AgmCoreModule } from '@agm/core';

@NgModule({
  imports: [
    AplikacjaDietetycznaSharedModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDvtihAY9wGuy0_VA8A-gxT4yFRISVoDqw'
    }),
    RouterModule.forChild(SklepyRoute),
    ReactiveFormsModule
  ],
  declarations: [SklepyComponent]
})
export class AplikacjaDietetycznaSklepyModule {}
