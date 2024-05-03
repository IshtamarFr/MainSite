import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-sudoku',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sudoku.component.html',
  styleUrl: './sudoku.component.scss',
})
export class SudokuComponent {
  S: (number | null)[] = Array(81).fill(0);

  test(): void {
    window.alert(this.S.toString());
  }

  verify(rank: number): void {
    if (![null, 1, 2, 3, 4, 5, 6, 7, 8, 9].includes(this.S[rank]))
      this.S[rank] = null;
  }
}
