import { Routes } from '@angular/router';
import { GestmdpComponent } from './components/main/gestmdp.component';
import { PasswordFormComponent } from './components/password-form/password-form.component';

export const GESTMDP_ROUTES: Routes = [
  { path: '', redirectTo: '0', pathMatch: 'full' },
  { path: 'password', component: PasswordFormComponent },
  { path: 'password/:passwordId', component: PasswordFormComponent },
  { path: ':categoryId', component: GestmdpComponent },
];
