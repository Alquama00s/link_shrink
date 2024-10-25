import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class BaseService {
  constructor(protected snackBar: MatSnackBar) {}

  getheaders(): any {
    return {
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    };
  }

  onError(resp: any) {
    this.snackBar.open(resp.error.message, undefined, {
      duration: 1500,
      panelClass: ['snack-bar-red'],
    });
  }
}
