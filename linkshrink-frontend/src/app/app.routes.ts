import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { inject } from '@angular/core';
import { AuthService } from './services/auth.service';

export const routes: Routes = [
  { path: 'web/login', component: LoginComponent },
  {
    path: 'web',
    canActivate:[()=>inject(AuthService).isLoggedIn()],
    children: [
      {
        path: '',
        component: HomeComponent,
      },
    ],
  },
  {
    path: '**',
    redirectTo: '/web/login',
  },
];
