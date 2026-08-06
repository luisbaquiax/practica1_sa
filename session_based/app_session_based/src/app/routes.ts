import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home.component';
import { SessionLoginComponent } from './pages/session/session-login.component';
import { SessionProtectedComponent } from './pages/session/session-protected.component';
import { SessionPublicComponent } from './pages/session/session-public.component';
import { SessionDashboardComponent } from './pages/session/session-dashboard.component';
import { ComparisonComponent } from './pages/comparison.component';
import { sessionGuard } from './guards/session.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent, title: 'Inicio' },
  {
    path: 'session',
    children: [
      { path: '', component: SessionDashboardComponent, title: 'Session-Based' },
      { path: 'login', component: SessionLoginComponent, title: 'Login (Session)' },
      { path: 'public', component: SessionPublicComponent, title: 'Página Pública (Session)' },
      { path: 'protected', component: SessionProtectedComponent, title: 'Página Protegida (Session)', canActivate: [sessionGuard] },
    ],
  },
  { path: 'comparison', component: ComparisonComponent, title: 'Comparación' },
  { path: '**', redirectTo: '' },
];
