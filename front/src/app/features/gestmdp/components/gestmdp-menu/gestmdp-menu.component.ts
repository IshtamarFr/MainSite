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
import { DialogService } from '../../../../utils/dialog.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';

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
    MatSnackBarModule,
  ],
  templateUrl: './gestmdp-menu.component.html',
  styleUrl: './gestmdp-menu.component.scss',
})
export class GestmdpMenuComponent implements OnInit, OnDestroy {
  categories: Category[] = [];
  categoriesSubscription$!: Subscription;

  constructor(
    private categoryService: CategoryService,
    private dialogService: DialogService
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

  //TODO: Finir ce morceau de code pour créer des catégories
  newCategory(): void {
    this.dialogService
      .openInputDialog('Veuillez nommer la nouvelle catégorie', false)
      .pipe(take(1))
      .subscribe({
        next: (resp) => {},
        error: (_) => {},
      });
  }
}
