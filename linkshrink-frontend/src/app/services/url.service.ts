import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export class UrlService extends BaseService{
  constructor(private http: HttpClient) {
    super()
  }

  private token = null;
  private baseUrl = 'http://localhost:8080';
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  getUrls():Observable<any> {
    return this.http.get(this.baseUrl + '/api/urls/',{
      headers: this.getheaders()
    });

  }

  createUrl(url:any){
    return this.http.post(this.baseUrl + '/api/urls/create',url,{
      headers:this.getheaders()
    });
  }

  login() {}
}
