import { registerLocaleData } from '@angular/common';
import es from '@angular/common/locales/es';

registerLocaleData(es); // Esto registra el idioma español

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
