import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {NavigationComponent} from '../navigation/navigation.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NavigationComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: [
  ]
})
export class DashboardComponent {

}
