import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-gestmdp-menu',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './gestmdp-menu.component.html',
  styleUrl: './gestmdp-menu.component.scss',
})
export class GestmdpMenuComponent {
  public form = this.fb.group({
    category: ['', [Validators.required, Validators.maxLength(63)]],
  });

  constructor(private fb: FormBuilder) {}

  newCategory(): void {}
}
