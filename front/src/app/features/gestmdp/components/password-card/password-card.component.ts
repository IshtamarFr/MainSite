import { Component, Input } from '@angular/core';
import { Password } from '../../interfaces/password.interface';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-password-card',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatDividerModule],
  templateUrl: './password-card.component.html',
  styleUrl: './password-card.component.scss',
})
export class PasswordCardComponent {
  @Input() public password!: Password;

  public constructor() {}
}
