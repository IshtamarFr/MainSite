import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Category } from '../interfaces/category.interface';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private pathService = 'api/gestmdp/category';

  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.pathService);
  }
}
