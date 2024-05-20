import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { Category } from '../../interfaces/category.interface';
import { CategoryService } from '../../services/category.service';
import { Subscription, take } from 'rxjs';
import { MatCardModule } from '@angular/material/card';

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
    MatCardModule,
  ],
  templateUrl: './gestmdp-menu.component.html',
  styleUrl: './gestmdp-menu.component.scss',
})
export class GestmdpMenuComponent implements OnInit, OnDestroy {
  categories: Category[] = [];
  categoriesSubscription$!: Subscription;

  public form = this.fb.group({
    category: ['', [Validators.required, Validators.maxLength(63)]],
  });

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.categoryService.categories$.subscribe({
      next: (resp) => {
        this.categories = resp.sort((a, b) => a.name.localeCompare(b.name));
      },
    });
  }

  ngOnDestroy(): void {
    if (this.categoriesSubscription$)
      this.categoriesSubscription$.unsubscribe();
  }

  newCategory(): void {}
}
