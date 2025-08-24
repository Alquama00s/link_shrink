import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from './base.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class UrlService extends BaseService {
  constructor(private http: HttpClient, protected override snackBar: MatSnackBar) {
    super(snackBar);
  }

  private token = null;
  private baseUrl = 'http://10.211.10.30:8014';
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  getUrls(): Observable<any> {
    return this.http
      .get(this.baseUrl + '/api/urls/', {
        headers: this.getheaders(),
      })
      .pipe((resp) => {
        resp.subscribe({ error: (res) => this.onError(res) });
        return resp;
      });
  }

  createUrl(url: any) {
    return this.http
      .post(this.baseUrl + '/api/urls/create', url, {
        headers: this.getheaders(),
      })
      .pipe((resp) => {
        resp.subscribe({ error: (res) => this.onError(res) });
        return resp;
      });
  }

  login() {}
}
