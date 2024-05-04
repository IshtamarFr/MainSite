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
      const P = service['setCellPossibilities'](service.S);
    } catch (error: any) {
      //Then
      expect(error).toBeInstanceOf(Error);
      expect(error.message).toBe('duplicate value 2 in ranks 36 and 41');
    }
  });
});
