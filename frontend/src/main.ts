
import { bootstrapApplication } from '@angular/platform-browser';

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { provideCharts } from 'ng2-charts';
import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import { tokenInterceptor } from './app/core/interceptors/token.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([tokenInterceptor])),
    provideRouter(routes),
    provideCharts() //
  ]
}).catch(err => console.error(err));
