import { Routes } from '@angular/router';
import { LoginComponent } from './modules/login/login.component';
import { LayoutComponent } from './modules/layout/layout.component';
import { HomeComponent } from './modules/home/home.component';
import { AlunosComponent } from './modules/alunos/alunos.component';
import { PlanosComponent } from './modules/planos/planos.component';
import { TreinoComponent } from './modules/treino/treino.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { DietaComponent } from './modules/dieta/dieta.component';
import { NutricionistaComponent } from './modules/nutricionista/nutricionista.component';
import { TreinoprofComponent } from './modules/treinoprof/treinoprof.component';
import { EscolherAlunoComponent } from './modules/escolher-aluno/escolher-aluno.component';


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
      { path: 'dashboard', component: DashboardComponent },
      {path: 'dieta', component: DietaComponent},
      {path: 'nutricionista/:id/:nome', component: NutricionistaComponent},
      {path: 'treinoprof', component: TreinoprofComponent},
      {path: 'escolher-aluno', component: EscolherAlunoComponent}
    ]
  }
];
