import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AplikacjaDietetycznaSharedModule } from 'app/shared/shared.module';
import { AplikacjaDietetycznaCoreModule } from 'app/core/core.module';
import { AplikacjaDietetycznaAppRoutingModule } from './app-routing.module';
import { AplikacjaDietetycznaHomeModule } from './home/home.module';
import { AplikacjaDietetycznaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AplikacjaDietetycznaSharedModule,
    AplikacjaDietetycznaCoreModule,
    AplikacjaDietetycznaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AplikacjaDietetycznaEntityModule,
    AplikacjaDietetycznaAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class AplikacjaDietetycznaAppModule {}
