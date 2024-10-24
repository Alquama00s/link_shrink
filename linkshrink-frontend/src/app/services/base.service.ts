import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BaseService {

  constructor() { }

  getheaders():any{
    return {
      Authorization: 'Bearer ' + localStorage.getItem("token"),
    };
  }


}
