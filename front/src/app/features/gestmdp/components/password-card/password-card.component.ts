import { Component, Input } from '@angular/core';
import { Password } from '../../interfaces/password.interface';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-password-card',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatButtonModule,
    MatIconModule,
    MatSlideToggleModule,
    MatTooltipModule,
    FormsModule,
    MatSnackBarModule,
  ],
  templateUrl: './password-card.component.html',
  styleUrl: './password-card.component.scss',
})
export class PasswordCardComponent {
  @Input() public password!: Password;

  public constructor(private _snackBar: MatSnackBar) {}

  changeStatus(): void {
    this.password.active = !this.password.active;
  }

  async copySiteLogin(password: Password): Promise<void> {
    await navigator.clipboard.writeText(password.siteLogin!);
    this._snackBar.open('Login copié dans le presse-papiers', 'fermer', {
      duration: 2500,
    });
  }
}
