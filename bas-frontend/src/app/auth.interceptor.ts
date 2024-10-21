import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service'; // Import your AuthService

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('AuthInterceptor is called');
    const token = this.authService.getToken();  // Get the JWT token

    // If the token exists, clone the request and add the token to the headers
    if (token && (typeof token !== 'undefined') && token !== "undefined") {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(cloned);
    }

    // If no token is present, continue without modifying the request
    return next.handle(req);
  }
}
