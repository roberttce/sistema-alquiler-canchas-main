import { Routes } from '@angular/router';
import { LoginComponent } from './components/Landing/login/login.component';
import { InicioComponent } from './components/Landing/inicio/inicio.component';
import { HomeComponent } from './components/PaginaWeb/home/home.component';
import { ReservausuarioComponent } from './components/PaginaWeb/reservausuario/reservausuario.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'inicio', component: InicioComponent },
    { path: 'home', component: HomeComponent }, //ruta para HomeComponent
    { path: 'reservausuario', component: ReservausuarioComponent }
];