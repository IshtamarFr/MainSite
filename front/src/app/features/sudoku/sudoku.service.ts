import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SudokuService {
  public S: (number | null)[] = [...Array(81)].map((_) => null); //Sudoku grid as it's used in service

  /*
  Functions dealing with Lines, Colums, and Sectors (LCS)
  */
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

    //Doesn't use getSector, as I prefer having the top-left index for sector instead
    for (var comp = 0; comp < 3; comp++) {
      for (var comp2 = 0; comp2 < 3; comp2++) {
        const candidate = 9 * (baseline + comp) + basecolumn + comp2;
        if (candidate !== rank) answer.push(candidate);
      }
    }
    return answer;
  }

  private getAllRanksInLine(li: number): number[] {
    let answer: number[] = [];
    for (var comp = 0; comp < 9; comp++) {
      answer.push(comp + 9 * li);
    }
    return answer;
  }

  private getAllRanksInColumn(co: number): number[] {
    let answer: number[] = [];
    for (var comp = 0; comp < 9; comp++) {
      answer.push(9 * comp + co);
    }
    return answer;
  }

  private getAllRanksInSector(sector: number): number[] {
    let answer: number[] = [];

    const baseCell = 27 * Math.floor(sector / 3) + 3 * (sector % 3);
    for (var comp = 0; comp < 3; comp++) {
      for (var comp2 = 0; comp2 < 3; comp2++) {
        answer.push(baseCell + 9 * comp + comp2);
      }
    }
    return answer;
  }

  private getOtherRanksInLCS(rank: number): number[] {
    const set1 = new Set([
      ...this.getOtherRanksinLine(rank),
      ...this.getOtherRanksinColumn(rank),
      ...this.getOtherRanksinSector(rank),
    ]);
    return Array.from(set1).sort((a, b) => a - b);
  }

  /*
  Functions dealing with logics
  */
  private setCellPossibilities(grid: (number | null)[]): number[][] {
    let P: number[][] = [...Array(81)].map((_) => [
      9, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    ]); //Initializes all 81 cases to allow all 9 numbers

    for (let rank of [...Array(81).keys()]) {
      if (grid[rank] != null) {
        //I get value of grid[rank] and set it to all OK
        const value = grid[rank]!;
        P[rank] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

        for (let rank2 of this.getOtherRanksInLCS(rank)) {
          if (grid[rank2] == null) {
            //I work to disallow value to all cells in line, column, sector
            if (P[rank2][value] === 1) {
              P[rank2][value] = 0;

              if (P[rank2][0] > 1) P[rank2][0]--;
              else
                throw new Error(
                  `cell in rank ${rank2} is not set but has 0 possibilities`
                );
            }
          } else {
            //I check if cell is not a duplicate in line, column, sector
            if (grid[rank2] === value)
              throw new Error(
                `duplicate value ${value} in ranks ${rank} and ${rank2}`
              );
          }
        }
      }
    }
    return P;
  }

  public nextMovesGrid(grid: (number | null)[]): (number | null)[] {
    let N: (number | null)[] = [...Array(81)].map((_) => null);
    let P = this.setCellPossibilities(grid);

    //This part only deals with cells (with 0-1 possibilities to fill in)
    P.forEach((item, index) => {
      if (grid[index] == null) {
        if (item[0] === 1) {
          //I know there's only one value possible for N[index] which is not set up
          item[0] = 0;
          N[index] = item.findIndex((value) => value === 1);
        }
      }
    });

    //Now we try to apply all actions
    console.log(this.findAll1CandidatesActions(grid, P));
    for (let action of this.findAll1CandidatesActions(grid, P)) {
      if (N[action[0]] === null) {
        N[action[0]] = action[1];
      } else if (N[action[0]] !== action[1]) {
        throw new Error(
          `cell ${action[0]} can reaceive both only a ${N[action[0]]} and a ${
            action[1]
          } from different methods`
        );
      }
    }
    console.log(N);
    return N;
  }

  private valuesFor1Candidates(block: (number | null)[]): number[] {
    let answer: Set<number> = new Set([1, 2, 3, 4, 5, 6, 7, 8, 9]);

    block.forEach((value) => {
      if (value != null) answer.delete(value);
    });
    return Array.from(answer).sort((a, b) => a - b);
  }

  private coreFor1Candidates(
    block: (number | null)[],
    pBlock: number[][],
    indexes: number[]
  ): [number, number][] {
    //From a list of 9 values from grid and P, we return array of [rank (in real table),value]
    //Error can be thrown if a value should be set but has no room
    let answer: [number, number][] = [];

    for (let value of this.valuesFor1Candidates(block)) {
      let candidates: number[] = [];

      for (let index of [0, 1, 2, 3, 4, 5, 6, 7, 8]) {
        if (pBlock[index][value] === 1) candidates.push(index);
      }

      if (candidates.length === 0) {
        throw new Error(
          `value ${value} is not set in block ${block.toString()}, but has no room for that`
        );
      } else if (candidates.length === 1) {
        answer.push([indexes[candidates[0]], value]);
      }
    }

    return answer;
  }

  private blockify(array: any[], indexes: number[]): any[] {
    let answer: any[] = [];
    for (let i of indexes) {
      answer.push(array[i]);
    }
    return answer;
  }

  private findAll1CandidatesActions(
    grid: (number | null)[],
    P: number[][]
  ): [number, number][] {
    let actions: [number, number][] = [];
    let indexes: number[];

    for (let comp = 0; comp < 9; comp++) {
      indexes = this.getAllRanksInLine(comp);
      actions.push(
        ...this.coreFor1Candidates(
          this.blockify(grid, indexes),
          this.blockify(P, indexes),
          indexes
        )
      );
      indexes = this.getAllRanksInColumn(comp);
      actions.push(
        ...this.coreFor1Candidates(
          this.blockify(grid, indexes),
          this.blockify(P, indexes),
          indexes
        )
      );
      indexes = this.getAllRanksInSector(comp);
      actions.push(
        ...this.coreFor1Candidates(
          this.blockify(grid, indexes),
          this.blockify(P, indexes),
          indexes
        )
      );
    }
    return actions;
  }

  /*
  Functions dealing with filling (simple, complex, montecarlo)
  */
  private concatGrid(
    grid1: (number | null)[],
    grid2: (number | null)[]
  ): (number | null)[] {
    let answer = [...grid1];

    answer.forEach((value, index) => {
      if (grid2[index] != null) {
        if (value == null) {
          answer[index] = grid2[index];
        } else if (grid2[index] !== value) {
          throw new Error(
            `cannot concat ${grid1.toString()} and ${grid2.toString()} (index: ${index})`
          );
        }
      }
    });
    return answer;
  }

  public simpleFill(grid: (number | null)[]): (number | null)[] {
    let F1: (number | null)[] = [...grid];
    let oldGridLength: number = F1.filter((x) => x !== null).length;
    let newGridLength: number;
    let toBeContinued: boolean = oldGridLength < 81;

    while (toBeContinued) {
      F1 = this.concatGrid(F1, this.nextMovesGrid(F1));

      newGridLength = F1.filter((x) => x !== null).length;
      toBeContinued = newGridLength < 81 && newGridLength > oldGridLength;
      oldGridLength = newGridLength;
    }

    console.log(F1);
    return F1;
  }
}
