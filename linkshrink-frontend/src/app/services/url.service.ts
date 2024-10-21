import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  constructor(private http: HttpClient) {}

  private token = null;
  private baseUrl = 'http://localhost';
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  getUrls():Observable<any> {
    return this.http.get(this.apiUrl);
  }

  login() {}
}
