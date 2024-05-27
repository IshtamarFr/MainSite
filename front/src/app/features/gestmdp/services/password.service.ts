import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Password } from '../interfaces/password.interface';
import { Observable, of } from 'rxjs';
import { PasswordRequest } from '../interfaces/password-request.interface';

@Injectable({
  providedIn: 'root',
})
export class PasswordService {
  private pathService = 'api/gestmdp';

  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<Password[]> {
    return this.httpClient.get<Password[]>(`${this.pathService}/password`);
  }

  public getById(id: number | string): Observable<Password> {
    return this.httpClient.get<Password>(`${this.pathService}/password/${id}`);
  }

  public calculate(password: Password, key: string): Observable<string> {
    return this.httpClient.post(
      `${this.pathService}/password/${password.id}`,
      key,
      { responseType: 'text' }
    );
  }

  public delete(password: Password): Observable<string> {
    return this.httpClient.delete(
      `${this.pathService}/password/${password.id}`,
      { responseType: 'text' }
    );
  }

  public create(
    request: PasswordRequest,
    category: string | number
  ): Observable<Password> {
    return this.httpClient.post<Password>(
      `${this.pathService}/category/${category}/password`,
      request
    );
  }

  public modify(
    passwordId: number,
    request: PasswordRequest,
    category_id: string | number
  ): Observable<Password> {
    return this.httpClient.put<Password>(
      `${this.pathService}/password/${passwordId}`,
      request,
      { params: { category_id } }
    );
  }

  public toggleStatus(
    passwordId: number,
    status: boolean
  ): Observable<Password> {
    return this.httpClient.put<Password>(
      `${this.pathService}/password/${passwordId}/status`,
      null,
      { params: { status } }
    );
  }
}
