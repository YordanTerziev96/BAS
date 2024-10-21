import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';
import {CookieService} from 'ngx-cookie-service'; // If using cookies to store the JWT
import {jwtDecode} from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl: string = 'http://localhost:8080/api/auth';  // Adjust to your API endpoint
  private tokenKey: string = 'auth_token';

  constructor(private http: HttpClient, private cookieService: CookieService) {
  }

  // Login method
  login(username: string, password: string): Observable<any> {
    const payload = {username, password};
    return this.http.post(`${this.baseUrl}/signin`, payload);
  }

  register(userData: any) {
    return this.http.post(`${this.baseUrl}/signup`, userData).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';

    // Custom user-friendly messages based on status code
    switch (error.status) {
      case 400:
        errorMessage = 'Please check your input and try again.'; // Bad Request
        break;
      case 401:
        errorMessage = 'You are not authorized to perform this action.'; // Unauthorized
        break;
      case 409:
        errorMessage = 'This username or email is already taken.'; // Conflict
        break;
      case 500:
        errorMessage = 'An error occurred on the server. Please try again later.'; // Internal Server Error
        break;
      default:
        errorMessage = `Error: ${error.message}`; // Fallback message
        break;
    }

    // Return an observable with a user-friendly error message using throwError
    return throwError(() => new Error(errorMessage)); // Updated usage of throwError
  }

  // Save token in localStorage or cookies
  saveToken(token: string): void {
    this.cookieService.set(this.tokenKey, token, 1, '/');  // Set JWT token in a cookie
  }

  // Retrieve token from cookies or localStorage
  getToken(): string {
    return this.cookieService.get(this.tokenKey);
  }

  // Check if the user is authenticated based on token
  isAuthenticated(): boolean {
    const token = this.getToken();
    if (token && (typeof token !== "undefined") && token !== "undefined") {
      const decoded: any = jwtDecode(token);  // Decode the JWT token
      return decoded.exp > Date.now() / 1000;  // Check if token is expired
    }
    return false;
  }

  // Get user details from the token
  getUserDetails(): any {
    const token = this.getToken();
    return token ? jwtDecode(token) : null;
  }

  // Logout method
  logout(): void {
    this.cookieService.delete(this.tokenKey);
  }
}
