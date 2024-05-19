import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Password } from '../interfaces/password.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PasswordService {
  private pathService = 'api/gestmdp';

  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<Password[]> {
    return this.httpClient.get<Password[]>(`${this.pathService}/password`);
  }
}
