import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Subscription, take } from 'rxjs';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../interfaces/category.interface';
import { Password } from '../../interfaces/password.interface';
import { PasswordService } from '../../services/password.service';
import { PasswordCardComponent } from '../password-card/password-card.component';
import { FormsModule } from '@angular/forms';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { DialogService } from '../../../../utils/dialog.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-gestmdp',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    PasswordCardComponent,
    FormsModule,
    MatSlideToggleModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
  ],
  templateUrl: './gestmdp.component.html',
  styleUrl: './gestmdp.component.scss',
})
export class GestmdpComponent implements OnInit, OnDestroy {
  public category: Category | undefined = undefined;
  public categories: Category[] = [];
  public passwords: Password[] = [];
  public search: string = '';

  public categoriesSubscription$!: Subscription;
  public passwordsSubscription$!: Subscription;

  public isInactiveShown: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private passwordService: PasswordService,
    private dialogService: DialogService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.categoryService.fetchData().subscribe({
      next: (resp) => {
        this.categories = resp.sort((a, b) => a.name.localeCompare(b.name));
        this.categoriesSubscription$ = this.activatedRoute.params.subscribe(
          (params) => {
            this.category = this.categories.find(
              (x) => x.id == params['categoryId']
            );
          }
        );
      },
    });

    this.refreshPasswords();
  }

  ngOnDestroy(): void {
    if (this.categoriesSubscription$)
      this.categoriesSubscription$.unsubscribe();
  }

  refreshPasswords(): void {
    this.passwordService
      .getAll()
      .pipe(take(1))
      .subscribe({
        next: (resp) => {
          this.passwords = resp.sort((a, b) =>
            a.siteName.localeCompare(b.siteName)
          );
        },
      });
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
