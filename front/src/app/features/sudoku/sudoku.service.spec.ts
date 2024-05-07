import { TestBed } from '@angular/core/testing';
import { SudokuService } from './sudoku.service';

describe('SudokuService', () => {
  let service: SudokuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SudokuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getLine, getColum and getSector work', () => {
    //Test for rank 5
    expect(service['getLine'](5)).toBe(0);
    expect(service['getColumn'](5)).toBe(5);
    expect(service['getSector'](5)).toBe(1);

    //Test for rank 41
    expect(service['getLine'](41)).toBe(4);
    expect(service['getColumn'](41)).toBe(5);
    expect(service['getSector'](41)).toBe(4);

    //Test for rank 46
    expect(service['getLine'](46)).toBe(5);
    expect(service['getColumn'](46)).toBe(1);
    expect(service['getSector'](46)).toBe(3);

    //Test for rank 69
    expect(service['getLine'](69)).toBe(7);
    expect(service['getColumn'](69)).toBe(6);
    expect(service['getSector'](69)).toBe(8);
  });

  it('getOtherRanksinLine/Column/Sector work', () => {
    //Test for rank 12
    expect(service['getOtherRanksinLine'](12)).toStrictEqual([
      9, 10, 11, 13, 14, 15, 16, 17,
    ]);
    expect(service['getOtherRanksinColumn'](12)).toStrictEqual([
      3, 21, 30, 39, 48, 57, 66, 75,
    ]);
    expect(service['getOtherRanksinSector'](12)).toStrictEqual([
      3, 4, 5, 13, 14, 21, 22, 23,
    ]);

    //Test for rank 62
    expect(service['getOtherRanksinLine'](62)).toStrictEqual([
      54, 55, 56, 57, 58, 59, 60, 61,
    ]);
    expect(service['getOtherRanksinColumn'](62)).toStrictEqual([
      8, 17, 26, 35, 44, 53, 71, 80,
    ]);
    expect(service['getOtherRanksinSector'](62)).toStrictEqual([
      60, 61, 69, 70, 71, 78, 79, 80,
    ]);
  });

  it('getOtherRanksInLCS work', () => {
    expect(service['getOtherRanksInLCS'](30)).toStrictEqual([
      3, 12, 21, 27, 28, 29, 31, 32, 33, 34, 35, 39, 40, 41, 48, 49, 50, 57, 66,
      75,
    ]);
    expect(service['getOtherRanksInLCS'](64)).toStrictEqual([
      1, 10, 19, 28, 37, 46, 54, 55, 56, 63, 65, 66, 67, 68, 69, 70, 71, 72, 73,
      74,
    ]);
  });

  it('setCellPossibilities work for cell 23', () => {
    //Given
    service.S = Array(81).fill(null);
    service.S[23] = 7;

    //When
    const P = service['setCellPossibilities'](service.S);

    //Then
    expect(P[21][7]).toBe(0);
    expect(P[21][6]).toBe(1);
    expect(P[21][0]).toBe(8);

    expect(P[33][7]).toBe(1);
    expect(P[33][6]).toBe(1);
    expect(P[33][0]).toBe(9);

    expect(P[23][7]).toBe(0);
    expect(P[23][6]).toBe(0);
    expect(P[23][0]).toBe(0);
  });

  it('setCellPossibilities throw error for duplicates', () => {
    //Given
    service.S = Array(81).fill(null);
    service.S[41] = 2;
    service.S[36] = 2;

    //When
    try {
      service['setCellPossibilities'](service.S);
    } catch (error: any) {
      //Then
      expect(error).toBeInstanceOf(Error);
      expect(error.message).toBe('duplicate value 2 in ranks 36 and 41');
    }
  });

  it('setCellPossibilities throw error for 0 possibilities', () => {
    //Given
    service.S = Array(81).fill(null);
    service.S[0] = 1;
    service.S[1] = 2;
    service.S[2] = 3;
    service.S[3] = 4;
    service.S[4] = 5;
    service.S[5] = 6;
    service.S[6] = 7;
    service.S[7] = 8;

    //When
    const P = service['setCellPossibilities'](service.S);

    //Then
    expect(P[8]).toStrictEqual([1, 0, 0, 0, 0, 0, 0, 0, 0, 1]);

    //And Given
    service.S[25] = 9;

    //When
    try {
      service['setCellPossibilities'](service.S);
    } catch (error: any) {
      //Then
      expect(error).toBeInstanceOf(Error);
      expect(error.message).toBe(
        'cell in rank 8 is not set but has 0 possibilities'
      );
    }
  });

  it('nextMoveGrid works for cell where there is one candidate only (line)', () => {
    //Given
    service.S = Array(81).fill(null);
    service.S[0] = 1;
    service.S[1] = 2;
    service.S[2] = 3;
    service.S[3] = 4;
    service.S[4] = 5;
    service.S[5] = 6;
    service.S[6] = 7;
    service.S[7] = 8;

    //When
    const N = service.nextMovesGrid(service.S);

    //Then
    expect(N[8]).toBe(9);
  });

  it('nextMoveGrid works for cell where there is one candidate only (sector)', () => {
    //Given
    service.S = Array(81).fill(null);
    service.S[30] = 1;
    service.S[31] = 2;
    service.S[32] = 3;
    service.S[39] = 4;
    service.S[40] = 5;
    service.S[48] = 7;
    service.S[49] = 8;
    service.S[50] = 9;

    //When
    const N = service.nextMovesGrid(service.S);

    //Then
    expect(N[41]).toBe(6);
  });

  it('valuesFor1Candidates send array of values to be worked with', () => {
    expect(
      service['valuesFor1Candidates']([null, null, null, 9, null, 3, 1, 7, 4])
    ).toStrictEqual([2, 5, 6, 8]);
    expect(
      service['valuesFor1Candidates']([8, null, 1, 4, null, 5, null, null, 2])
    ).toStrictEqual([3, 6, 7, 9]);
  });

  it('getAllRanksinLine/Column/Sector work', () => {
    //Test for value 3
    expect(service['getAllRanksInLine'](3)).toStrictEqual([
      27, 28, 29, 30, 31, 32, 33, 34, 35,
    ]);
    expect(service['getAllRanksInColumn'](3)).toStrictEqual([
      3, 12, 21, 30, 39, 48, 57, 66, 75,
    ]);
    expect(service['getAllRanksInSector'](3)).toStrictEqual([
      27, 28, 29, 36, 37, 38, 45, 46, 47,
    ]);
  });

  it('coreFor1Candidates work when there is no error', () => {
    //Given
    //I try a cycle 3-4-7 within 3 cells, plus a 9 with only one cell possible
    const block = [null, null, null, 1, 6, 2, null, 5, 8];
    const pBlock = [
      [2, 0, 0, 0, 0, 0, 0, 1, 0, 1],
      [2, 0, 0, 0, 1, 0, 0, 1, 0, 0],
      [2, 0, 0, 1, 1, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [2, 0, 0, 1, 0, 0, 0, 1, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    ];

    //When
    const answers = service['coreFor1Candidates'](
      block,
      pBlock,
      [7, 16, 25, 34, 43, 52, 61, 70, 79]
    );

    //Then
    expect(answers).toStrictEqual([[7, 9]]);
  });

  it('coreFor1Candidates work when there is no error (2)', () => {
    //Given
    //I try a cycle 3-4-7-9 within 4 cells
    const block = [null, null, null, 1, 6, 2, null, 5, 8];
    const pBlock = [
      [2, 0, 0, 0, 0, 0, 0, 1, 0, 1],
      [2, 0, 0, 0, 1, 0, 0, 1, 0, 0],
      [3, 0, 0, 1, 1, 0, 0, 0, 0, 1],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [2, 0, 0, 1, 0, 0, 0, 1, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    ];

    //When
    const answers = service['coreFor1Candidates'](
      block,
      pBlock,
      [0, 1, 2, 3, 4, 5, 6, 7, 8]
    );

    //Then
    expect(answers).toStrictEqual([]);
  });

  it('coreFor1Candidates work when there is no error (2)', () => {
    //Given
    //I try a cycle 3-4-7 within 4 cells, 9 has no room
    const block = [null, null, null, 1, 6, 2, null, 5, 8];
    const pBlock = [
      [2, 0, 0, 0, 1, 0, 0, 1, 0, 0],
      [2, 0, 0, 0, 1, 0, 0, 1, 0, 0],
      [2, 0, 0, 1, 1, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [2, 0, 0, 1, 0, 0, 0, 1, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    ];

    //When
    try {
      service['coreFor1Candidates'](
        block,
        pBlock,
        [3, 4, 5, 12, 13, 14, 21, 22, 23]
      );
    } catch (error: any) {
      //Then
      expect(error).toBeInstanceOf(Error);
      expect(error.message).toBe(
        'value 9 is not set in block ,,,1,6,2,,5,8, but has no room for that'
      );
    }
  });

  it('blockify works', () => {
    //Given
    const block = [...Array(81).keys()].map((x) => x + 10);

    //When
    const W = service['blockify'](block, [57, 58, 59, 66, 67, 68, 75, 76, 77]);

    //Then
    expect(W).toStrictEqual([67, 68, 69, 76, 77, 78, 85, 86, 87]);
  });

  it('findAll1Candidates work', () => {
    //Given
    let S = Array(81).fill(null);
    S[0] = 1;
    S[12] = 1;
    S[51] = 1;
    S[79] = 1;

    //When
    const actions = service['findAll1CandidatesActions'](
      S,
      service['setCellPossibilities'](S)
    );

    //Then
    //Line, column and Sector makes it for 1 to be there only, so it is thriced
    expect(actions).toStrictEqual([
      [26, 1],
      [26, 1],
      [26, 1],
    ]);
  });

  it('concatGrid works', () => {
    //Given
    const A = [1, 7, null, null];
    const B = [null, 7, 3, null];

    //When
    const C = service['concatGrid'](A, B);

    //Then
    expect(C).toStrictEqual([1, 7, 3, null]);
  });

  it('concatGrid throws error on conflict', () => {
    //Given
    const A = [1, 7, null, null];
    const B = [null, 5, 3, null];

    //When
    try {
      service['concatGrid'](A, B);
    } catch (error: any) {
      //Then
      expect(error).toBeInstanceOf(Error);
      expect(error.message).toBe('cannot concat 1,7,, and ,5,3, (index: 1)');
    }
  });

  it('simpleFill works', () => {
    //Given
    const S = [
      null,
      null,
      4,
      null,
      1,
      7,
      null,
      null,
      3,
      5,
      null,
      null,
      null,
      null,
      2,
      null,
      null,
      null,
      null,
      null,
      6,
      4,
      null,
      null,
      1,
      8,
      null,
      null,
      2,
      null,
      null,
      6,
      9,
      8,
      null,
      null,
      6,
      null,
      null,
      null,
      2,
      null,
      null,
      null,
      5,
      null,
      null,
      9,
      7,
      3,
      null,
      null,
      4,
      null,
      null,
      9,
      1,
      null,
      null,
      5,
      6,
      null,
      null,
      null,
      null,
      null,
      2,
      null,
      null,
      null,
      null,
      8,
      7,
      null,
      null,
      9,
      8,
      null,
      3,
      null,
      null,
    ];

    //When
    const answer = service.simpleFill(S);

    //Then
    expect(answer).toStrictEqual([
      9, 8, 4, 6, 1, 7, 5, 2, 3, 5, 1, 3, 8, 9, 2, 4, 6, 7, 2, 7, 6, 4, 5, 3, 1,
      8, 9, 4, 2, 7, 5, 6, 9, 8, 3, 1, 6, 3, 8, 1, 2, 4, 7, 9, 5, 1, 5, 9, 7, 3,
      8, 2, 4, 6, 8, 9, 1, 3, 4, 5, 6, 7, 2, 3, 4, 5, 2, 7, 6, 9, 1, 8, 7, 6, 2,
      9, 8, 1, 3, 5, 4,
    ]);
  });

  it('find2CandidateCells works', () => {
    //Given
    const P: number[][] = [
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [2, 0, 1, 0, 0, 0, 0, 0, 1, 0],
      [3, 1, 1, 1, 0, 0, 0, 0, 0, 0],
    ];

    //When
    const W = service['find2CandidateCells'](P);

    //Then
    expect(W).toStrictEqual([[1, 2, 8]]);
  });

  it('complexFill works', () => {
    //Given
    const S = [
      null,
      null,
      5,
      null,
      null,
      8,
      9,
      null,
      3,
      null,
      8,
      null,
      9,
      null,
      null,
      4,
      null,
      null,
      null,
      null,
      null,
      null,
      6,
      null,
      null,
      7,
      null,
      null,
      null,
      null,
      null,
      8,
      null,
      null,
      9,
      2,
      null,
      null,
      8,
      null,
      5,
      null,
      6,
      null,
      null,
      2,
      4,
      null,
      null,
      7,
      null,
      null,
      null,
      null,
      null,
      2,
      null,
      null,
      3,
      null,
      null,
      null,
      null,
      null,
      null,
      9,
      null,
      null,
      1,
      null,
      3,
      null,
      8,
      null,
      7,
      4,
      null,
      null,
      2,
      null,
      null,
    ];

    //When
    const answer = service.complexFill(S);

    //Then
    expect(answer).toStrictEqual([
      1, 6, 5, 7, 4, 8, 9, 2, 3, 7, 8, 2, 9, 1, 3, 4, 5, 6, 3, 9, 4, 2, 6, 5, 1,
      7, 8, 5, 7, 6, 1, 8, 4, 3, 9, 2, 9, 1, 8, 3, 5, 2, 6, 4, 7, 2, 4, 3, 6, 7,
      9, 5, 8, 1, 4, 2, 1, 5, 3, 7, 8, 6, 9, 6, 5, 9, 8, 2, 1, 7, 3, 4, 8, 3, 7,
      4, 9, 6, 2, 1, 5,
    ]);
  });

  it('findMontecarloCandidate should work (random values)', () => {
    //Given
    const S = [
      null,
      2,
      9,
      null,
      null,
      null,
      4,
      null,
      null,
      null,
      null,
      null,
      5,
      null,
      null,
      1,
      null,
      null,
      null,
      4,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      4,
      2,
      null,
      null,
      null,
      6,
      null,
      null,
      null,
      null,
      null,
      null,
      7,
      null,
      5,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      7,
      null,
      null,
      3,
      null,
      null,
      null,
      null,
      5,
      null,
      1,
      null,
      null,
      9,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      6,
      null,
    ];

    const P = service['setCellPossibilities'](S);

    //When (tries on 30 different values)
    for (let comp = 0; comp < 30; comp++) {
      const action = service['findMontecarloCandidate'](S);

      //Then
      expect(S[action[0]]).toBeNull();
      expect(P[action[0]][action[1]]).toBe(1);
    }
  });

  it('Monte should work (random values)', () => {
    //Given
    const S = [
      null,
      2,
      9,
      null,
      null,
      null,
      4,
      null,
      null,
      null,
      null,
      null,
      5,
      null,
      null,
      1,
      null,
      null,
      null,
      4,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      4,
      2,
      null,
      null,
      null,
      6,
      null,
      null,
      null,
      null,
      null,
      null,
      7,
      null,
      5,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      7,
      null,
      null,
      3,
      null,
      null,
      null,
      null,
      5,
      null,
      1,
      null,
      null,
      9,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      6,
      null,
    ];

    //When
    const F1 = service['montecarloFill'](S, 10000);

    //Then
    expect(F1).toStrictEqual([
      3, 2, 9, 8, 1, 6, 4, 5, 7, 8, 6, 7, 5, 3, 4, 1, 9, 2, 1, 4, 5, 2, 7, 9, 6,
      3, 8, 9, 3, 1, 7, 4, 2, 5, 8, 6, 6, 8, 4, 1, 5, 3, 2, 7, 9, 5, 7, 2, 9, 6,
      8, 3, 1, 4, 7, 9, 6, 3, 2, 1, 8, 4, 5, 4, 1, 8, 6, 9, 5, 7, 2, 3, 2, 5, 3,
      4, 8, 7, 9, 6, 1,
    ]);
  });
});
