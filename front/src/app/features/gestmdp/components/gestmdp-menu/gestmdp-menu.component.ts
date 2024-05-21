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
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

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
    private dialogService: DialogService,
    private _snackBar: MatSnackBar
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

  newCategory(): void {
    this.dialogService
      .openInputDialog('Veuillez nommer la nouvelle catégorie', false)
      .pipe(take(1))
      .subscribe({
        next: (resp) => {
          if (resp)
            this.categoryService
              .createCategory(resp)
              .pipe(take(1))
              .subscribe({
                next: (newCategory) => {
                  this.categories.push(newCategory);
                  this.categories.sort((a, b) => a.name.localeCompare(b.name));
                },
                error: (_) =>
                  this._snackBar.open("Une erreur s'est produite", 'fermer', {
                    duration: 2500,
                  }),
              });
        },
        error: (_) =>
          this._snackBar.open("Une erreur s'est produite", 'fermer', {
            duration: 2500,
          }),
      });
  }
}
