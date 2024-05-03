import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SudokuService {
  public S: (number | null)[] = Array(81).fill(0);

  private getLine(rank: number): number {
    return Math.floor(rank / 9);
  }

  private getColumn(rank: number): number {
    return rank % 9;
  }

  private getSector(rank: number): number {
    return 3 * Math.floor(rank / 27) + Math.floor((rank % 9) / 3);
  }

  private getOtherRanksinLine(rank: number): number[] {
    let answer: number[] = [];
    const base = 9 * this.getLine(rank);
    for (var comp = 0; comp < 9; comp++) {
      if (comp + base !== rank) answer.push(comp + base);
    }
    return answer;
  }

  private getOtherRanksinColumn(rank: number): number[] {
    let answer: number[] = [];
    const base = this.getColumn(rank);
    for (var comp = 0; comp < 9; comp++) {
      if (9 * comp + base !== rank) answer.push(9 * comp + base);
    }
    return answer;
  }

  private getOtherRanksinSector(rank: number): number[] {
    let answer: number[] = [];
    const baseline = 3 * Math.floor(this.getLine(rank) / 3);
    const basecolumn = 3 * Math.floor(this.getColumn(rank) / 3);
    for (var comp = 0; comp < 3; comp++) {
      for (var comp2 = 0; comp2 < 3; comp2++) {
        const candidate = 9 * (baseline + comp) + basecolumn + comp2;
        if (candidate !== rank) answer.push(candidate);
      }
    }
    return answer;
  }

  private getOtherRanksinLCS(rank: number): number[] {
    const set1 = new Set([
      ...this.getOtherRanksinLine(rank),
      ...this.getOtherRanksinColumn(rank),
      ...this.getOtherRanksinSector(rank),
    ]);
    return Array.from(set1).sort((a, b) => a - b);
  }
}
