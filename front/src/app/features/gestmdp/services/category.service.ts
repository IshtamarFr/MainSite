import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, shareReplay } from 'rxjs';
import { Category } from '../interfaces/category.interface';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private readonly pathService = 'api/gestmdp/category';
  public categories$: Observable<Category[]>;

  constructor(private httpClient: HttpClient) {
    this.categories$ = this.fetchData().pipe(shareReplay());
  }

  fetchData(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.pathService).pipe(
      catchError((error) => {
        console.error('Error fetching categories:', error);
        return [];
      })
    );
  }

  createCategory(name: string): Observable<Category> {
    return this.httpClient.post<Category>(this.pathService, null, {
      params: { name },
    });
  }
}
