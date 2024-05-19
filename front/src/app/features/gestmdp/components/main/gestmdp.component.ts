import { Component } from '@angular/core';
import { GestmdpMenuComponent } from '../gestmdp-menu/gestmdp-menu.component';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestmdp',
  standalone: true,
  imports: [GestmdpMenuComponent, MatCardModule, CommonModule],
  templateUrl: './gestmdp.component.html',
  styleUrl: './gestmdp.component.scss',
})
export class GestmdpComponent {}
