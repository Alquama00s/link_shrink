import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {}

  private token = null;
  private baseUrl = 'http://localhost:8082';
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  login(email: string, password: string): Observable<any> {
    const req = {
      email,
      password,
    };
    return this.http.post<any>(this.baseUrl + '/api/user/login', req).pipe((res) => {
      res.subscribe((res) => {
        console.log(res)
        this.token = res.token
      });
      return res;
    });
  }

  profile(): Observable<any> {
    return this.http.get(this.baseUrl + '/profile', {
      headers: {
        Authorization: 'Bearer ' + this.token,
      },
    });
  }

  isLoggedIn(): Observable<boolean> {
    return of(this.token).pipe(
      map((v) => {
        if (v != null) {
          return true;
        } else {
          this.router.navigate(['web','login'])
          return false;
        }
      })
    );
  }
}
