import {Component} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule
  ],
  styleUrls: [
    // '../../assets/bootstrap/css/bootstrap.css',
    // '../../assets/slitslider/css/custom.css',
    // '../../assets/slitslider/css/style.css'
  ]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  login(): void {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        this.authService.saveToken(response.response.token);  // Save token on successful login
        this.router.navigate(['/homepage']);  // Redirect to dashboard or other authenticated route
      },
      (error) => {
        this.errorMessage = 'Invalid credentials';  // Display error if login fails
      }
    );
  }
}
