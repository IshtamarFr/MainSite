import { Routes } from '@angular/router';
import { SudokuComponent } from './main/sudoku.component';
import { SudokuDetailsComponent } from './sudoku-details/sudoku-details.component';

export const SUDOKU_ROUTES: Routes = [
  { path: '', component: SudokuComponent },
  { path: 'details', component: SudokuDetailsComponent },
];
