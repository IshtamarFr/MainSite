import { Component } from '@angular/core';
import { GestmdpMenuComponent } from '../gestmdp-menu/gestmdp-menu.component';

@Component({
  selector: 'app-gestmdp',
  standalone: true,
  imports: [GestmdpMenuComponent],
  templateUrl: './gestmdp.component.html',
  styleUrl: './gestmdp.component.scss',
})
export class GestmdpComponent {}
