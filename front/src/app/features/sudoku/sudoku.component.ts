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
  N: (number | null)[] = [...Array(81)].map((_) => null); //Next moves possible
  nextValues: Set<number> = new Set();

  isModified: boolean = false;
  isValuesShowed: boolean = false;
  error: string = '';

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
    this.N = [...Array(81)].map((_) => null);
    this.nextValues = new Set();
    this.isValuesShowed = false;

    this.sudokuService.S.forEach((item, index) => {
      if (!item && this.T[index]) this.sudokuService.S[index] = this.T[index];
    });
  }

  showPossible(): void {
    this.error = '';
    this.N = [...Array(81)].map((_) => null);

    try {
      this.N = this.sudokuService.nextMovesGrid(this.sudokuService.S);
    } catch (error: any) {
      this.error = error.message;
    }
  }

  showValues(): void {
    this.nextValues = new Set();
    this.N.forEach((x) => {
      if (x !== null) this.nextValues.add(x);
    });
    this.isValuesShowed = !this.isValuesShowed;
  }

  fillValues(): void {
    this.error = '';
    this.N = [...Array(81)].map((_) => null);
    this.nextValues = new Set();

    try {
      const temp = this.sudokuService.nextMovesGrid(this.sudokuService.S);
      this.N = temp;
      this.T = temp;
    } catch (error: any) {
      this.error = error.message;
    }
  }

  simpleFill(): void {
    this.error = '';
    this.N = [...Array(81)].map((_) => null);
    this.nextValues = new Set();

    try {
      this.T = this.sudokuService.simpleFill(this.sudokuService.S);
    } catch (error: any) {
      this.error = error.message;
    }
  }

  complexFill(): void {
    this.error = '';
    this.N = [...Array(81)].map((_) => null);
    this.nextValues = new Set();

    try {
      this.T = this.sudokuService.complexFill(this.sudokuService.S);
    } catch (error: any) {
      this.error = error.message;
    }
  }

  montecarloFill(iterations: number): void {
    this.error = '';
    this.N = [...Array(81)].map((_) => null);
    this.nextValues = new Set();

    if (this.sudokuService.S.length > 16) {
      //If grid is under 17 cells, it cannot be completed
      try {
        this.T = this.sudokuService.montecarloFill(
          this.sudokuService.S,
          iterations
        );
      } catch (error: any) {
        this.error = error.message;
      }
    }
  }
}
