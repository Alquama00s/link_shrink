import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, of } from 'rxjs';
import { BaseService } from './base.service';
import { Buffer } from 'buffer';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends BaseService {
  constructor(private http: HttpClient, private router: Router) {
    super();
    const tempToken = localStorage.getItem('token');
    if (!this.isExpired(tempToken)) {
      this.token = tempToken!;
    }
  }

  private token: string | null = null;
  private savedProfile: any | null = null;
  private authnUrl = 'http://localhost:8082';
  private shortnerUrl = 'http://localhost:8080';

  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  login(email: string, password: string): Observable<any> {
    const req = {
      email,
      password,
    };
    return this.http
      .post<any>(this.authnUrl + '/api/user/login', req)
      .pipe((res) => {
        res.subscribe((res) => {
          this.token = res.token;
          localStorage.setItem('token', res.token);
        });
        return res;
      });
  }

  signUp(name: string, email: string, password: string): Observable<any> {
    const req = {
      name,
      email,
      password,
    };
    return this.http.post<any>(this.authnUrl + '/api/user/register', req);
  }

  isExpired(token: string | null): boolean {
    if (token == null) return true;
    try {
      var header = JSON.parse(
        Buffer.from(token.split('.')[0], 'base64').toString('binary')
      );
      return Math.floor(Date.now() / 1000) < header.exp;
    } catch (ex) {
      console.log(ex);
    }

    return false;
  }

  profile(): Observable<any> {
    if (this.savedProfile != null) return of(this.savedProfile);
    return this.http
      .get(this.shortnerUrl + '/api/user/profile', {
        headers: this.getheaders(),
      })
      .pipe((res) => {
        res.subscribe((res) => {
          this.savedProfile = res;
        });
        return res;
      });
  }

  isLoggedIn(): Observable<boolean> {
    return of(this.token).pipe(
      map((v) => {
        if (this.isExpired(v)) {
          return true;
        } else {
          this.router.navigate(['web', 'login']);
          return false;
        }
      })
    );
  }
}
