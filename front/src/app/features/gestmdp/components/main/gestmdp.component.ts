import { Component, OnDestroy, OnInit } from '@angular/core';
import { GestmdpMenuComponent } from '../gestmdp-menu/gestmdp-menu.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../interfaces/category.interface';
import { Password } from '../../interfaces/password.interface';
import { PasswordService } from '../../services/password.service';
import { PasswordCardComponent } from '../password-card/password-card.component';
import { FormsModule } from '@angular/forms';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-gestmdp',
  standalone: true,
  imports: [
    GestmdpMenuComponent,
    CommonModule,
    RouterModule,
    PasswordCardComponent,
    FormsModule,
    MatSlideToggleModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './gestmdp.component.html',
  styleUrl: './gestmdp.component.scss',
})
export class GestmdpComponent implements OnInit, OnDestroy {
  public category: Category | undefined = undefined;
  public categories: Category[] = [];
  public passwords: Password[] = [];

  public categoriesSubscription$!: Subscription;
  public passwordsSubscription$!: Subscription;

  public isInactiveShown: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private passwordService: PasswordService
  ) {}

  ngOnInit(): void {
    this.categoryService.categories$.subscribe({
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

    this.passwordsSubscription$ = this.passwordService.getAll().subscribe({
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

  removePassword(password: Password): void {
    this.passwords = this.passwords.filter((x) => {
      x.id != password.id;
    });
  }
}
