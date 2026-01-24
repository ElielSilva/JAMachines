import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.api}/auth/login`, { email, password });
  }

  register(name: string, email: string, password: string): Observable<{token: string}> {
    return this.http.post<{ token: string }>(`${this.api}/auth/register`, { name, email, password });
  }

  getToken(): string | null {
    return sessionStorage.getItem('authToken');
  }

  setToken(token: string) {
    sessionStorage.setItem('authToken', token);
  }

  isLoggedIn(): boolean {
    try {
      const token = sessionStorage.getItem('authToken') || '';
      if(!token) return false
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expirationTime = payload.exp * 1000;
      return Date.now() < expirationTime;
    } catch (e) {
      return false;
    }
  }
}
