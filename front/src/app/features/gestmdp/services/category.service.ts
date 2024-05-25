import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Category } from '../interfaces/category.interface';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private readonly pathService = 'api/gestmdp/category';

  constructor(private httpClient: HttpClient) {}

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

  deleteCategory(category: Category): Observable<string> {
    return this.httpClient.delete(`${this.pathService}/${category.id}`, {
      responseType: 'text',
    });
  }

  modifyCategory(category: Category, name: string): Observable<Category> {
    return this.httpClient.put<Category>(
      `${this.pathService}/${category.id}`,
      null,
      {
        params: { name },
      }
    );
  }
}
