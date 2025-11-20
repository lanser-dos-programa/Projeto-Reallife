import { Routes } from '@angular/router';
import { LoginComponent } from './modules/login/login.component';
import { LayoutComponent } from './modules/layout/layout.component';
import { HomeComponent } from './modules/home/home.component';
import { AlunosComponent } from './modules/alunos/alunos.component';
import { PlanosComponent } from './modules/planos/planos.component';
import { TreinoComponent } from './modules/treino/treino.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'alunos', component: AlunosComponent },
      { path: 'planos', component: PlanosComponent },
      { path: 'treino', component: TreinoComponent },
      { path: 'dashboard', component: DashboardComponent }
    ]
  }
];
