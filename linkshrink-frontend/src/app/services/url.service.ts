import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  constructor(private http: HttpClient) {
  }

  private token = null;
  private baseUrl = 'http://localhost:8082';
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  getUrls(): Observable<any> {
    return this.http
      .get(this.baseUrl + '/api/urls/')
  }

  createUrl(url: any) {
    return this.http
      .post(this.baseUrl + '/api/urls/create', url)
  }

   getSuggestion(url: string): Observable<any>{
    return this.http.post(this.baseUrl + '/api/urls/suggest', {
      longUrl: url
    });
   }

  login() {}
}
