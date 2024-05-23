import { Component, EventEmitter, Input, Output } from '@angular/core';
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
import { DialogService } from '../../../../utils/dialog.service';
import { PasswordService } from '../../services/password.service';
import { take } from 'rxjs';
import { RouterModule } from '@angular/router';

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
    RouterModule,
  ],
  templateUrl: './password-card.component.html',
  styleUrl: './password-card.component.scss',
})
export class PasswordCardComponent {
  @Input() public password!: Password;
  @Output() public deletedPassword = new EventEmitter<Password>();

  public constructor(
    private _snackBar: MatSnackBar,
    private dialogService: DialogService,
    private passwordService: PasswordService
  ) {}

  changeStatus(): void {
    this.password.active = !this.password.active;
  }

  async copySiteLogin(password: Password): Promise<void> {
    await navigator.clipboard.writeText(password.siteLogin!);
    this._snackBar.open('Login copié dans le presse-papiers', 'fermer', {
      duration: 2500,
    });
  }

  calculatePassword(password: Password): void {
    this.dialogService
      .openInputDialog('Veuillez entrer votre clé privée', true)
      .subscribe({
        next: (key) => {
          if (key) {
            this.passwordService
              .calculate(password, key)
              .pipe(take(1))
              .subscribe({
                next: async (realPassword) => {
                  await navigator.clipboard.writeText(realPassword);
                  this._snackBar.open(
                    'MdP copié dans le presse-papiers',
                    'fermer',
                    {
                      duration: 2500,
                    }
                  );
                },
                error: (_) =>
                  this._snackBar.open("Une erreur s'est produite", 'fermer', {
                    duration: 2500,
                  }),
              });
          }
        },
      });
  }

  deletePassword(password: Password): void {
    this.dialogService
      .openConfirmDialog(
        'Etes-vous sûr de vouloir supprimer ce mot de passe ?',
        true
      )
      .subscribe({
        next: (resp) => {
          if (resp)
            this.passwordService
              .delete(password)
              .pipe(take(1))
              .subscribe({
                next: (resp) => {
                  this.deletedPassword.emit(password);
                },
                error: (_) => {
                  this._snackBar.open("Une erreur s'est produite", 'fermer', {
                    duration: 2500,
                  });
                },
              });
        },
      });
  }
}
