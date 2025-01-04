import { Routes } from '@angular/router';
import { LoginComponent } from './components/Landing/login/login.component';
import { InicioComponent } from './components/Landing/inicio/inicio.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'inicio', component: InicioComponent }
];
