import { Routes } from '@angular/router';
import { GestmdpComponent } from './components/main/gestmdp.component';

export const GESTMDP_ROUTES: Routes = [
  { path: '', component: GestmdpComponent },
  { path: ':categoryId', component: GestmdpComponent },
];
