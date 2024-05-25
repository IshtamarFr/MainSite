import { Component, NgZone, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
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
import { CategoryCardComponent } from '../category-card/category-card.component';

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
    MatIconModule,
    CategoryCardComponent,
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
  public isModified: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private passwordService: PasswordService,
    private dialogService: DialogService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.refreshCategories();
    this.refreshPasswords();
    this.categoriesSubscription$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.category = this.categories.find(
          (x) => x.id == params['categoryId']
        );
      }
    );
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

  refreshCategories(): void {
    this.categoryService
      .fetchData()
      .pipe(take(1))
      .subscribe({
        next: (resp) => {
          this.categories = resp.sort((a, b) => a.name.localeCompare(b.name));
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

  deletedCategory(category: Category): void {
    if (category == this.category)
      this.ngZone.run(() => {
        this.router.navigate(['/gestmdp', 0]);
      });
    this.refreshCategories();
  }

  modifiedCategory(): void {
    this.refreshCategories();
  }
}
