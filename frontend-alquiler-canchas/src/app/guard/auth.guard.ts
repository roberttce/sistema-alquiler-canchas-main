import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  
  const token = localStorage.getItem('token');

  if (!token) {
    const router = new Router();
    router.navigate(['/api/auth/login']); // Redirigir al login si no hay token
    return false;
  }

  return true; // Permite el acceso si hay un token
};
