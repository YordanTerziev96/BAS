import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavigationComponent} from './navigation/navigation.component';
import {AuthService} from './auth.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NavigationComponent, RouterOutlet, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: [
    // '../assets/bootstrap/css/bootstrap.css',
    // '../assets/slitslider/css/custom.css',
    // '../assets/slitslider/css/style.css'
  ]
})
export class AppComponent {
  title = 'bas-frontend';
  constructor(public authService: AuthService) {}
}
