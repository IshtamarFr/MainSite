import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Category } from '../../interfaces/category.interface';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { DialogService } from '../../../../utils/dialog.service';
import { CategoryService } from '../../services/category.service';
import { take } from 'rxjs';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
  ],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.scss',
})
export class CategoryCardComponent {
  @Input() public category!: Category;
  @Input() public isModified!: boolean;
  @Output() public deletedCategory = new EventEmitter<void>();
  @Output() public modifiedCategory = new EventEmitter<void>();

  public constructor(
    private dialogService: DialogService,
    private categoryService: CategoryService,
    private _snackBar: MatSnackBar
  ) {}

  deleteCategory(): void {
    this.dialogService
      .openConfirmDialog('Voulez-vous supprimer cette catégorie ?', true)
      .pipe(take(1))
      .subscribe({
        next: (resp) => {
          if (resp)
            this.categoryService
              .deleteCategory(this.category)
              .pipe(take(1))
              .subscribe({
                next: (_) => {
                  this.deletedCategory.emit();
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

  modifyCategory(): void {
    this.dialogService
      .openInputDialog('Veuillez renommer cette catégorie', false)
      .pipe(take(1))
      .subscribe({
        next: (resp) => {
          if (resp)
            this.categoryService
              .modifyCategory(this.category, resp)
              .pipe(take(1))
              .subscribe({
                next: (_) => {
                  this.modifiedCategory.emit();
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
