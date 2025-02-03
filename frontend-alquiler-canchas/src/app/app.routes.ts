import { Routes } from '@angular/router';
import { AdministradorListarComponent } from './component/administrador/administrador-listar/administrador-listar.component';
import { AdministradorCrearComponent } from './component/administrador/administrador-crear/administrador-crear.component';
import { ClienteListarComponent } from './component/cliente/cliente-listar/cliente-listar.component';
import { ClienteCrearComponent } from './component/cliente/cliente-crear/cliente-crear.component';
import { LoginComponent } from './component/login/login/login.component';
import { InicioComponent } from './component/inicio/inicio/inicio.component';
import { ReservasComponent } from './component/reservas/reservas.component';
import { authGuard } from './guard/auth.guard';

export const routes: Routes = [
	{ path: '', redirectTo: '/api/auth/login', pathMatch: 'full' }, // Redirige al Login
	
	{ path: 'api/auth/login', component: LoginComponent },

	// Rutas protegidas con authGuard
	{ path: 'inicio', component: InicioComponent, canActivate: [authGuard] },
	{ path: 'reservas', component: ReservasComponent, canActivate: [authGuard] },

	{ path: 'api/administradores/crear', component: AdministradorCrearComponent, canActivate: [authGuard] },
	{ path: 'api/administradores/getall', component: AdministradorListarComponent, canActivate: [authGuard] },

	{ path: 'api/clientes', component: ClienteListarComponent, canActivate: [authGuard] },
	{ path: 'api/clientes/crear', component: ClienteCrearComponent, canActivate: [authGuard] },

];
