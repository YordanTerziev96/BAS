import {Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {RegisterComponent} from './register/register.component';
import {authGuard} from './auth/auth.guard';
import {HomepageComponent} from './homepage/homepage.component';
import {SliderComponent} from './slider/slider.component';
import {SearchComponent} from './search/search.component';
import {BannerSearchComponent} from './banner-search/banner-search.component';

export const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent, canActivate: [authGuard]},
  // {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  {path: 'homepage', component: HomepageComponent, canActivate: [authGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [authGuard]},
  // {path: 'slider', component: SliderComponent, canActivate: [authGuard]},//TODO remove
  // {path: 'search', component: SearchComponent, canActivate: [authGuard]},//TODO remove
  // {path: 'bannersearch', component: BannerSearchComponent, canActivate: [authGuard]}//TODO remove
  // Define other routes here
];
