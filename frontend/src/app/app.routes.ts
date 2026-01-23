import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
// import { DashboardComponent } from './pages/auth/auth.routes';
// import { AuthRoutingModule } from './pages/auth/auth-routing.module';
import { AuthGuard } from './core/guard/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },

  {
    path: 'auth',
    loadChildren: () =>
      import('./pages/auth/auth-routing.module')
        .then(m => m.AuthRoutingModule)
  },

  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component')
        .then(m => m.DashboardComponent),
    canActivate: [AuthGuard],
    children: [
      {
        path: 'machines',
        loadComponent: () => 
          import('./components/machines-list/machines-list.component')
            .then(m => m.MachinesComponent)
      },
      {
        path: 'logs',
        loadComponent: () => 
          // Ajustado para a pasta root /components
          import('./components/machines-logs/machines-logs.component') 
            .then(m => m.MachinesLogsComponent)
      },
      {
        path: '',
        redirectTo: 'machines',
        pathMatch: 'full'
      }
    ]
  },

  {
    path: '**',
    redirectTo: 'auth/login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

