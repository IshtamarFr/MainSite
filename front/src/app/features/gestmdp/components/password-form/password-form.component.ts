import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-password-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './password-form.component.html',
  styleUrl: './password-form.component.scss',
})
export class PasswordFormComponent {
  public form = this.fb.group({
    siteName: [
      '',
      [Validators.required, Validators.maxLength(63), Validators.minLength(3)],
    ],
    siteAddress: ['', [Validators.maxLength(255), Validators.minLength(3)]],
    siteLogin: ['', [Validators.maxLength(127), Validators.minLength(3)]],
    passwordPrefix: ['', [Validators.maxLength(4), Validators.minLength(4)]],
    passwordLength: ['', [Validators.max(64), Validators.min(8)]],
    description: ['', [Validators.maxLength(500)]],
  });

  public constructor(private fb: FormBuilder) {}
}
