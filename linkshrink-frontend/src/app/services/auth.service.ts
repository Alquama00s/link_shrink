import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  of,
  tap,
  throwError,
} from 'rxjs';
import { Buffer } from 'buffer';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {
    const tempToken = localStorage.getItem('token');
    if (!this.isExpired(tempToken)) {
      this.token.next(tempToken!);
    }
  }

  private token: BehaviorSubject<string> = new BehaviorSubject('');
  private savedProfile: any | null = null;
  private authnUrl = 'http://localhost:8083';
  private shortnerUrl = 'http://localhost:8082';

  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  login(email: string, password: string): Observable<any> {
    const req = {
      email,
      password,
    };
    return this.http.post<any>(this.authnUrl + '/api/user/login', req).pipe(
      tap((res) => {
        this.token.next(res.token);
        localStorage.setItem('token', res.token);
      })
    );
  }

  logOut() {
    this.token.next('');
    localStorage.clear();
    this.router.navigate(['web', 'login']);
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
      return Math.floor(Date.now() / 1000) > header.exp;
    } catch (ex) {
      console.log(ex);
    }

    return true;
  }

  profile(): Observable<any> {
    if (this.savedProfile != null) return of(this.savedProfile);
    return this.http
      .get(this.shortnerUrl + '/api/user/profile')
      .pipe(
        tap((res) => {
          this.savedProfile = res;
        })
      );
  }

  isLoggedIn(): Observable<boolean> {
    return this.token.pipe(
      map((v) => {
        if (!this.isExpired(v)) {
          return true;
        } else {
          this.router.navigate(['web', 'login']);
          return false;
        }
      })
    );
  }
}
