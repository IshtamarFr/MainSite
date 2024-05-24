import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  NgZone,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../interfaces/category.interface';
import { take } from 'rxjs';
import { PasswordRequest } from '../../interfaces/password-request.interface';
import { PasswordService } from '../../services/password.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-password-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  templateUrl: './password-form.component.html',
  styleUrl: './password-form.component.scss',
})
export class PasswordFormComponent implements OnInit {
  public action: string = 'Création';
  public categories!: Category[];

  public form = this.fb.group({
    siteName: [
      '',
      [Validators.required, Validators.maxLength(63), Validators.minLength(3)],
    ],
    siteAddress: ['', [Validators.maxLength(255), Validators.minLength(3)]],
    siteLogin: ['', [Validators.maxLength(127), Validators.minLength(3)]],
    category: ['', [Validators.required]],
    passwordPrefix: ['', [Validators.maxLength(4), Validators.minLength(4)]],
    passwordLength: ['', [Validators.max(64), Validators.min(8)]],
    description: ['', [Validators.maxLength(500)]],
  });

  public constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private passwordService: PasswordService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (window.location.href.includes('password/'))
      this.action = 'Modification';
    this.categoryService
      .fetchData()
      .pipe(take(1))
      .subscribe({ next: (resp) => (this.categories = resp) });
  }

  submit(): void {
    const category = this.form.get('category')!.value;
    this.form.get('category')?.disable();
    const formData = this.form.value as PasswordRequest;
    this.form.get('category')?.enable();

    if (this.action === 'Création') {
      this.passwordService.create(formData, category!).subscribe({
        next: (_) => {
          this.back();
        },
        error: (_) =>
          this._snackBar.open("Une erreur s'est produite", 'fermer', {
            duration: 2500,
          }),
      });
    }
  }

  back(): void {
    window.history.back();
  }
}
