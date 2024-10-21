import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService); // Inject AuthService
  const router = inject(Router); // Inject Router

  // Get the current route path
  const currentPath = route.routeConfig?.path;

  // Check if the user is authenticated
  if (authService.isAuthenticated()) {
    // If the user is authenticated and trying to access login route,
    // redirect them to the dashboard (or any other route you want)
    if (currentPath === 'login' || currentPath === 'register' ) {
      router.navigate(['/homepage']); // Redirect to dashboard if already logged in
      return false; // Prevent access to login page
    }
    return true; // If authenticated and accessing other protected routes, allow access
  } else {
    // If not authenticated, allow access to login and redirect if trying to access other routes
    if (currentPath === 'register') {
      return true;
    }

    if (currentPath !== 'login') {
      router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
    }
    return true; // Allow access to login page if not authenticated
  }
};
