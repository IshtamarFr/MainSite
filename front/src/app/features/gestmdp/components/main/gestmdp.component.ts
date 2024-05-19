import { Component, OnDestroy, OnInit } from '@angular/core';
import { GestmdpMenuComponent } from '../gestmdp-menu/gestmdp-menu.component';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Subscription, take } from 'rxjs';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../interfaces/category.interface';
import { Password } from '../../interfaces/password.interface';
import { PasswordService } from '../../services/password.service';

@Component({
  selector: 'app-gestmdp',
  standalone: true,
  imports: [GestmdpMenuComponent, MatCardModule, CommonModule, RouterModule],
  templateUrl: './gestmdp.component.html',
  styleUrl: './gestmdp.component.scss',
})
export class GestmdpComponent implements OnInit, OnDestroy {
  public category: Category | undefined = undefined;
  public categories: Category[] = [];
  public passwords: Password[] = [];

  public categoriesSubscription$!: Subscription;
  public passwordsSubscription$!: Subscription;

  constructor(
    private activatedRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private passwordService: PasswordService
  ) {}

  ngOnInit(): void {
    this.categoryService
      .getAll()
      .pipe(take(1))
      .subscribe({
        next: (resp) => {
          this.categories = resp;
          this.categoriesSubscription$ = this.activatedRoute.params.subscribe(
            (params) => {
              this.category = this.categories.find(
                (x) => x.id == params['categoryId']
              );
            }
          );
        },
      });

    this.passwordsSubscription$ = this.passwordService
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

  ngOnDestroy(): void {
    if (this.categoriesSubscription$)
      this.categoriesSubscription$.unsubscribe();
    if (this.passwordsSubscription$) this.passwordsSubscription$.unsubscribe();
  }
}
