import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, of } from 'rxjs';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends BaseService{

  constructor(private http: HttpClient, private router: Router) {
    super()
    const tempToken = localStorage.getItem("token")
    if(!this.isExpired(tempToken)){
      this.token = tempToken!;
    }
  }

  private token:String|null = null;
  private savedProfile:any|null =null;
  private baseUrl = 'http://localhost:8082';
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  login(email: string, password: string): Observable<any> {
    const req = {
      email,
      password,
    };
    return this.http.post<any>(this.baseUrl + '/api/user/login', req).pipe((res) => {
      res.subscribe((res) => {
        this.token = res.token
        localStorage.setItem("token",res.token)
      });
      return res;
    });
  }

  isExpired(token:string|null):boolean{
    if(token==null)return true;
    return false;
  }

  profile(): Observable<any> {
    if(this.savedProfile!=null)return of(this.savedProfile);
    return this.http.get(this.baseUrl + '/api/user/profile', {
      headers: this.getheaders(),
    }).pipe((res)=>{
      res.subscribe((res)=>{
        this.savedProfile = res;
      })
      return res;
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
