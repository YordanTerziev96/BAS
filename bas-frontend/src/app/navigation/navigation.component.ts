import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navigation.component.html',
  styleUrls: [
    // '../../assets/bootstrap/css/bootstrap.css',
    // '../../assets/slitslider/css/custom.css',
    // '../../assets/slitslider/css/style.css'
  ]
})
export class NavigationComponent {
  constructor(private authService: AuthService, private router: Router) {}

  logout() {
    this.authService.logout();  // Call logout method from AuthService
    this.router.navigate(['/login']);  // Redirect to login page
  }

}
