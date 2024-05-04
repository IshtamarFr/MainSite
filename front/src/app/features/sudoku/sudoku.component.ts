import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SudokuService } from './sudoku.service';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-sudoku',
  standalone: true,
  imports: [CommonModule, FormsModule, MatButtonModule, MatSlideToggleModule],
  templateUrl: './sudoku.component.html',
  styleUrl: './sudoku.component.scss',
})
export class SudokuComponent {
  S: (number | null)[]; //Sudoku grid as it's already known
  T: (number | null)[] = [...Array(81)].map((_) => null); //Temporary grid as it's filled
  isModified: boolean = false;

  constructor(private sudokuService: SudokuService) {
    this.S = sudokuService.S;
  }

  test(): void {
    window.alert(this.T.toString());
  }

  verify(rank: number): void {
    if (![null, 1, 2, 3, 4, 5, 6, 7, 8, 9].includes(this.T[rank]))
      this.T[rank] = null;
  }

  validate(): void {
    this.sudokuService.S.forEach((item, index) => {
      if (!item && this.T[index]) this.sudokuService.S[index] = this.T[index];
    });
  }

  showPossible(): void {}
}
