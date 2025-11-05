import { Routes } from '@angular/router';
import { LoginComponent } from './modules/login/login.component';
import { HomeComponent } from './modules/home/home.component';
import { AlunosComponent } from './modules/alunos/alunos.component';
import { PlanosComponent } from './modules/planos/planos.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'alunos', component: AlunosComponent },
  {path: 'planos', component: PlanosComponent }
];
