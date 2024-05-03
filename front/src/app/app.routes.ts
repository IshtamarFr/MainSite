import { Routes } from '@angular/router';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { IshtaComponent } from './features/dev/ishta/ishta.component';
import { SudokuComponent } from './features/sudoku/sudoku.component';

export const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import('./features/auth/auth.routes').then((r) => r.AUTH_ROUTES),
  },
  {
    path: 'main',
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./features/main/main.routes').then((r) => r.MAIN_ROUTES),
  },
  { path: 'sudoku', component: SudokuComponent },
  { path: '404', component: NotFoundComponent },
  { path: 'ishta', component: IshtaComponent },

  { path: '**', redirectTo: '404' },
];
