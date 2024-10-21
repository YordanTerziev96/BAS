import { Component } from '@angular/core';
import {SliderComponent} from '../slider/slider.component';
import {BannerSearchComponent} from '../banner-search/banner-search.component';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [SliderComponent, BannerSearchComponent],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {

}
